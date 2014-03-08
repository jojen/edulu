package org.jojen.wikistudy.controller;

import org.jojen.wikistudy.entity.*;
import org.jojen.wikistudy.service.BlobService;
import org.jojen.wikistudy.service.ContentService;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;
import org.jojen.wikistudy.util.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@Controller
@RequestMapping("/content")
public class ContentController {
	protected static final int DEFAULT_PAGE_NUM = 0;
	protected static final int DEFAULT_PAGE_SIZE = 5;

	@Inject
	protected CourseService courseService;

	@Inject
	protected LessonService lessonService;

	@Inject
	protected ContentService contentService;

	@Inject
	protected BlobService blobService;

	protected static final Logger log = LoggerFactory
												.getLogger(ContentController.class);

	@RequestMapping(value = "/delete/{lesson}/{content}", method = RequestMethod.GET)
	public String delete(           @PathVariable(value = "lesson") Integer lid ,
								  @PathVariable(value = "content") Integer id,Model model){


		Lesson lesson = lessonService.findById(lid);
		Content content = contentService.findById(id);

		contentService.move(lesson,content.getPosition()-1,lesson.getContent().size()-1);
		lesson.getContent().remove(content);
		lessonService.update(lesson);


		contentService.deleteById(id);
		model.addAttribute("self",true);
		return "/json/boolean";
	}
	@RequestMapping(value = "/property", method = RequestMethod.GET)
	public String propertyChange(@RequestParam(value = "name") String type,
								 @RequestParam(value = "id") Integer id,
								 @RequestParam(value = "value") String value){
		Content c = contentService.findById(id);
		// TODO hier müsste man dann reflections machen

		((Image)c).setShowPdf(Boolean.valueOf(value));
		contentService.update(c);

		return "/json/boolean";
	}


	@RequestMapping(value = "/{type}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "type") String type,
					   @RequestParam(value = "id", required = false) Integer id,
					   @RequestParam(value = "lessonid", required = false) Integer lessonid,
					   @RequestParam(value = "courseid", required = false) Integer courseid, Model model) {
		if (id == null) {
			if (type.equals("text")) {
				model.addAttribute(new Text());
			}
			if (type.equals("quiz")) {
				model.addAttribute(new Quiz());
			}

		} else {
			model.addAttribute(contentService.findById(id));
		}
		model.addAttribute("lessonid", lessonid);
		model.addAttribute("courseid", courseid);

		return "/content/" + type + ".edit";
	}


	@RequestMapping(value = "/quiz/edit", method = RequestMethod.POST)
	public String editQuiz(@Valid Quiz quiz,
						   @RequestParam(value = "id") Integer id,
						   @RequestParam(value = "quizcontent") String quizcontent,
						   @RequestParam(value = "lessonid") Integer lessonid,
						   @RequestParam(value = "courseid") Integer courseid,
						   Model model) {
		log.debug("edit quiz={}");

		Quiz modelQuiz;
		Lesson l = null;
		if (id == null) {
			modelQuiz = new Quiz();
			l = lessonService.findById(lessonid);
			l.addContent(modelQuiz);
			modelQuiz.setPosition(l.getContent().size());

		} else {
			modelQuiz = (Quiz) contentService.findById(id);
		}
		modelQuiz.setName(quiz.getName());
		modelQuiz.setQuizContent(quizcontent);


		contentService.update(modelQuiz);
		if (l != null) {
			lessonService.update(l);
		}
		return "redirect:/course/" + courseid + "/lesson/" + lessonid;
	}

	@RequestMapping(value = "/text/edit", method = RequestMethod.POST)
	public String editText(@Valid Text text,
						   BindingResult bindingResult,
						   @RequestParam(value = "id", required = false) Integer id,
						   @RequestParam(value = "lessonid", required = true) Integer lessonid,
						   @RequestParam(value = "courseid", required = true) Integer courseid,
						   Model model) {
		log.debug("edit text={}", text);

		Text modelText;
		Lesson l = lessonService.findById(lessonid);
		if (id == null) {
			modelText = new Text();
			l.addContent(modelText);
			modelText.setPosition(l.getContent().size());
		} else {
			modelText = (Text) contentService.findById(id);
		}


		modelText.setName(text.getName());
		modelText.setText(text.getText());

		contentService.update(modelText);
		if (l != null) {
			lessonService.update(l);
		}
		return "redirect:/course/" + courseid + "/lesson/" + lessonid;
	}


	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String uploadFile(Model model, FileUpload fileUpload, @RequestParam(value = "id", required = false) Integer id) {
		log.debug("upload content={}", fileUpload);
		Lesson l = lessonService.findById(id);
		try {


			if (fileUpload.getFileData() != null) {
				Blobbased b;


				if (fileUpload.getFileData().getContentType().startsWith("image")) {
					b = new Image();
				} else if (fileUpload.getFileData().getContentType().startsWith("video")) {
					b = new Video();
				} else {
					b = new Download();
				}
				l.addContent(b);
				contentService.add(b,l);
				blobService.save(fileUpload.getFileData(), b.getId());
				b.setContentType(fileUpload.getFileData().getContentType());
				b.setName(UriUtils.encodeQuery(fileUpload.getFileData().getFileItem().getName(), "UTF8"));

				contentService.update(b);

				lessonService.update(l);

			}
		} catch (Exception e) {
			log.error("cannot upload file", e);
		}

		return "json/boolean";
	}

	private static final long DEFAULT_EXPIRE_TIME = 604800000L; // ..ms = 1 week.
	private static final int DEFAULT_BUFFER_SIZE = 10240; // ..bytes = 10KB.
	private static final String MULTIPART_BOUNDARY = "MULTIPART_BYTERANGES";

	@RequestMapping(value = "/media/{id}/{name}", method = RequestMethod.GET)

	public void media(@PathVariable("id") Integer id, HttpServletRequest request ,HttpServletResponse response) throws IOException {
		Content c = contentService.findById(id);
		if (c instanceof Blobbased) {
			Blobbased b = (Blobbased) c;
			File f = new File(blobService.get(b.getId()));


			// prepare some variables
			long length = f.length();
			long lastModified = f.lastModified();
			String eTag = id + "_" + lastModified;
			long expires = System.currentTimeMillis() + DEFAULT_EXPIRE_TIME;
			String disposition = "inline";

			if (b instanceof Download) {
				disposition = "attachment";
			}

			// Validate request headers for caching ---------------------------------------------------

			// If-None-Match header should contain "*" or ETag. If so, then return 304.
			String ifNoneMatch = request.getHeader("If-None-Match");
			if (ifNoneMatch != null && matches(ifNoneMatch, eTag)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", eTag); // Required in 304.
				response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
				return;
			}

			// If-Modified-Since header should be greater than LastModified. If so, then return 304.
			// This header is ignored if any If-None-Match header is specified.
			long ifModifiedSince = request.getDateHeader("If-Modified-Since");
			if (ifNoneMatch == null && ifModifiedSince != -1 && ifModifiedSince + 1000 > lastModified) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", eTag); // Required in 304.
				response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
				return;
			}

			// Validate request headers for resume ----------------------------------------------------

			// If-Match header should contain "*" or ETag. If not, then return 412.
			String ifMatch = request.getHeader("If-Match");
			if (ifMatch != null && !matches(ifMatch, eTag)) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
				return;
			}

			// If-Unmodified-Since header should be greater than LastModified. If not, then return 412.
			long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
			if (ifUnmodifiedSince != -1 && ifUnmodifiedSince + 1000 <= lastModified) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
				return;
			}

			// Validate and process range -------------------------------------------------------------

			// Prepare some variables. The full Range represents the complete file.
			Range full = new Range(0, length - 1, length);
			List<Range> ranges = new ArrayList<Range>();

			// Validate and process Range and If-Range headers.
			String range = request.getHeader("Range");
			if (range != null) {

				// Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
				if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
					response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
					response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
					return;
				}

				// If-Range header should either match ETag or be greater then LastModified. If not,
				// then return full file.
				String ifRange = request.getHeader("If-Range");
				if (ifRange != null && !ifRange.equals(eTag)) {
					try {
						long ifRangeTime = request.getDateHeader("If-Range"); // Throws IAE if invalid.
						if (ifRangeTime != -1 && ifRangeTime + 1000 < lastModified) {
							ranges.add(full);
						}
					} catch (IllegalArgumentException ignore) {
						ranges.add(full);
					}
				}

				// If any valid If-Range header, then process each part of byte range.
				if (ranges.isEmpty()) {
					for (String part : range.substring(6).split(",")) {
						// Assuming a file with length of 100, the following examples returns bytes at:
						// 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
						long start = sublong(part, 0, part.indexOf("-"));
						long end = sublong(part, part.indexOf("-") + 1, part.length());

						if (start == -1) {
							start = length - end;
							end = length - 1;
						} else if (end == -1 || end > length - 1) {
							end = length - 1;
						}

						// Check if Range is syntactically valid. If not, then return 416.
						if (start > end) {
							response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
							response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
							return;
						}

						// Add range.
						ranges.add(new Range(start, end, length));
					}
				}
			}


			// Prepare and initialize response --------------------------------------------------------

			// Get content type by file name and set default GZIP support and content disposition.
			String contentType =  b.getContentType();
			boolean acceptsGzip = false;


			// If content type is unknown, then set the default value.
			// For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
			// To add new content types, add new mime-mapping entry in web.xml.
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			// If content type is text, then determine whether GZIP content encoding is supported by
			// the browser and expand content type with the one and right character encoding.
			if (contentType.startsWith("text")) {
				String acceptEncoding = request.getHeader("Accept-Encoding");
				acceptsGzip = acceptEncoding != null && accepts(acceptEncoding, "gzip");
				contentType += ";charset=UTF-8";
			}

			// Else, expect for images, determine content disposition. If content type is supported by
			// the browser, then set to inline, else attachment which will pop a 'save as' dialogue.
			else if (!contentType.startsWith("image")) {
				String accept = request.getHeader("Accept");
				disposition = accept != null && accepts(accept, contentType) ? "inline" : "attachment";
			}

			response.reset();
			response.setBufferSize(DEFAULT_BUFFER_SIZE);
			response.setHeader("Content-Disposition", disposition + ";filename=\"" + c.getName() + "\"");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("ETag", eTag);
			response.setDateHeader("Last-Modified", lastModified);
			response.setDateHeader("Expires", expires);


			// Prepare streams.
			RandomAccessFile input = null;
			OutputStream output = null;

			try {
				// Open streams.
				input = new RandomAccessFile(f, "r");
				output = response.getOutputStream();

				if (ranges.isEmpty() || ranges.get(0) == full) {

					// Return full file.
					Range r = full;
					response.setContentType(contentType);
					response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);


						if (acceptsGzip) {
							// The browser accepts GZIP, so GZIP the content.
							response.setHeader("Content-Encoding", "gzip");
							output = new GZIPOutputStream(output, DEFAULT_BUFFER_SIZE);
						} else {
							// Content length is not directly predictable in case of GZIP.
							// So only add it if there is no means of GZIP, else browser will hang.
							response.setHeader("Content-Length", String.valueOf(r.length));
						}

						// Copy full range.
						copy(input, output, r.start, r.length);


				} else if (ranges.size() == 1) {

					// Return single part of file.
					Range r = ranges.get(0);
					response.setContentType(contentType);
					response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
					response.setHeader("Content-Length", String.valueOf(r.length));
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.


						// Copy single part range.
						copy(input, output, r.start, r.length);


				} else {

					// Return multiple parts of file.
					response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.


						// Cast back to ServletOutputStream to get the easy println methods.
						ServletOutputStream sos = (ServletOutputStream) output;

						// Copy multi part range.
						for (Range r : ranges) {
							// Add multipart boundary and header fields for every range.
							sos.println();
							sos.println("--" + MULTIPART_BOUNDARY);
							sos.println("Content-Type: " + contentType);
							sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

							// Copy single part range of multi part range.
							copy(input, output, r.start, r.length);
						}

						// End with multipart boundary.
						sos.println();
						sos.println("--" + MULTIPART_BOUNDARY + "--");

				}
			} finally {
				// Gently close streams.
				close(output);
				close(input);
			}

		}


	}

	/**
	 * Close the given resource.
	 * @param resource The resource to be closed.
	 */
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException ignore) {
				// Ignore IOException. If you want to handle this anyway, it might be useful to know
				// that this will generally only be thrown when the client aborted the request.
			}
		}
	}

	/**
	 * Copy the given byte range of the given input to the given output.
	 * @param input The input to copy the given range to the given output for.
	 * @param output The output to copy the given range from the given input for.
	 * @param start Start of the byte range.
	 * @param length Length of the byte range.
	 * @throws IOException If something fails at I/O level.
	 */
	private static void copy(RandomAccessFile input, OutputStream output, long start, long length)
			throws IOException
	{
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int read;

		if (input.length() == length) {
			// Write full range.
			while ((read = input.read(buffer)) > 0) {
				output.write(buffer, 0, read);
			}
		} else {
			// Write partial range.
			input.seek(start);
			long toRead = length;

			while ((read = input.read(buffer)) > 0) {
				if ((toRead -= read) > 0) {
					output.write(buffer, 0, read);
				} else {
					output.write(buffer, 0, (int) toRead + read);
					break;
				}
			}
		}
	}

	/**
	 * Returns true if the given accept header accepts the given value.
	 * @param acceptHeader The accept header.
	 * @param toAccept The value to be accepted.
	 * @return True if the given accept header accepts the given value.
	 */
	private static boolean accepts(String acceptHeader, String toAccept) {
		String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
		Arrays.sort(acceptValues);
		return Arrays.binarySearch(acceptValues, toAccept) > -1
					   || Arrays.binarySearch(acceptValues, toAccept.replaceAll("/.*$", "/*")) > -1
					   || Arrays.binarySearch(acceptValues, "*/*") > -1;
	}

	/**
	 * Returns true if the given match header matches the given value.
	 * @param matchHeader The match header.
	 * @param toMatch The value to be matched.
	 * @return True if the given match header matches the given value.
	 */
	private static boolean matches(String matchHeader, String toMatch) {
		String[] matchValues = matchHeader.split("\\s*,\\s*");
		Arrays.sort(matchValues);
		return Arrays.binarySearch(matchValues, toMatch) > -1
					   || Arrays.binarySearch(matchValues, "*") > -1;
	}

	/**
	 * Returns a substring of the given string value from the given begin index to the given end
	 * index as a long. If the substring is empty, then -1 will be returned
	 * @param value The string value to return a substring as long for.
	 * @param beginIndex The begin index of the substring to be returned as long.
	 * @param endIndex The end index of the substring to be returned as long.
	 * @return A substring of the given string value as long or -1 if substring is empty.
	 */
	private static long sublong(String value, int beginIndex, int endIndex) {
		String substring = value.substring(beginIndex, endIndex);
		return (substring.length() > 0) ? Long.parseLong(substring) : -1;
	}

	/**
	 * This class represents a byte range.
	 */
	protected class Range {
		long start;
		long end;
		long length;
		long total;

		/**
		 * Construct a byte range.
		 * @param start Start of the byte range.
		 * @param end End of the byte range.
		 * @param total Total length of the byte source.
		 */
		public Range(long start, long end, long total) {
			this.start = start;
			this.end = end;
			this.length = end - start + 1;
			this.total = total;
		}

	}


}
