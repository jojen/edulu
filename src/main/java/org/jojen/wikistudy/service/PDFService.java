package org.jojen.wikistudy.service;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.*;
import org.jojen.wikistudy.entity.*;
import org.jojen.wikistudy.entity.Container;
import org.jojen.wikistudy.entity.Image;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PDFService {
	@Inject
	BlobService blobService;

	Font h1 = new Font(Font.BOLD, 26);
	Font h2 = new Font(Font.BOLD, 24);
	Font h3 = new Font(Font.BOLD, 18);
	Font h4 = new Font(Font.BOLD, 14);

	Font titleFont = new Font(Font.UNDEFINED, 18, 1, new Color(200, 200, 200));


	public ByteArrayOutputStream getPdf(Course c) {
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, ret);
			writer.setStrictImageSequence(true);

			HeaderFooter event = new HeaderFooter();
			event.setTitle(c.getName());

			writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
			writer.setPageEvent(event);
			document.open();


			document.add(new Paragraph(c.getName(), h1));
			document.addTitle(c.getName());


			for (Element e : getRichText(c.getDescription())) {
				document.add(e);
			}
			Paragraph toctitle = new Paragraph("Lessons", h3);
			toctitle.setSpacingBefore(40);
			toctitle.setSpacingAfter(15);
			document.add(toctitle);
			com.lowagie.text.List lst1 = new com.lowagie.text.List(true, 15);
			// Inhaltsverzeichnis
			for (Lesson l : c.getLessons()) {
				lst1.add(l.getName());
			}
			document.add(lst1);

			int i = 0;
			for (Lesson l : c.getLessons()) {
				i++;
				Chapter chapter = new Chapter(new Paragraph(l.getName(), h2), i);
				if (i != 1) {
					chapter.setTriggerNewPage(false);

				}

				Section currentSection = null;

				for (Content content : l.getContent()) {
					currentSection = renderContent(chapter, currentSection, content);
				}
				document.add(chapter);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		return ret;
	}

	private Section renderContent(Chapter chapter, Section currentSection, Content content) throws IOException, DocumentException {
		Paragraph title = new Paragraph();
		if (content instanceof Text) {
			title = renderText(content, currentSection, null, chapter);
		}
		if (currentSection == null) {
			currentSection = chapter.addSection(title);
		}
		if (content instanceof org.jojen.wikistudy.entity.Image) {
			renderImage(currentSection, null, content, 300, 580);
		}
		if (content instanceof Container) {
			PdfPTable table = new PdfPTable(2);
			PdfPCell cellOne = new PdfPCell();
			Content subcontent = ((Container) content).getFirstContent();
			if (subcontent != null) {
				if (subcontent instanceof Text) {
					renderText(subcontent, null, cellOne, chapter);
				} else if (subcontent instanceof Image) {
					renderImage(null, cellOne, subcontent, 200, 300);
				}
			}

			PdfPCell cellTwo = new PdfPCell();
			Content subcontent2 = ((Container) content).getSecondContent();
			if (subcontent2 != null) {
				if (subcontent2 instanceof Text) {
					renderText(subcontent2, null, cellTwo, chapter);
				} else if (subcontent2 instanceof Image) {
					renderImage(null, cellTwo, subcontent2, 200, 300);
				}
			}
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			currentSection.add(table);

		}
		if (content instanceof Quiz) {
			renderQuiz(currentSection, null);
		}

		if (content instanceof Video) {
			renderVideo(currentSection, null);
		}
		if (content instanceof Download) {
			renderFile(currentSection, null);
		}

		return currentSection;
	}

	private Paragraph renderText(Content content, Section currentSection, PdfPCell cell, Chapter chapter) throws IOException, DocumentException {
		Paragraph title = null;
		if ((content).getName() != null || currentSection == null) {
			if (cell == null) {
				title = new Paragraph((content).getName(), h3);
				title.setSpacingAfter(7);
				title.setSpacingBefore(5);
				currentSection = chapter.addSection(title);
			}

		}

		for (Element e : getRichText(((Text) content).getText())) {
			if (currentSection != null) {
				currentSection.add(e);
			} else if (cell != null) {
				cell.addElement(e);
			}

		}
		return title;
	}

	private void renderImage(Section currentSection, PdfPCell cell, Content content, int height, int width) throws IOException, BadElementException {
		if (((org.jojen.wikistudy.entity.Image) content).getShowPdf()) {
			File file = new File(blobService.get(content.getId()));
			BufferedImage bufferedImage = ImageIO.read(file);
			com.lowagie.text.Image pdfimg = com.lowagie.text.Image.getInstance(bufferedImage, null);

			if (pdfimg.getHeight() > height || pdfimg.getWidth() > width || cell != null) {
				pdfimg.scaleToFit(height, width);
			} else {
				pdfimg.scaleToFit(pdfimg.getHeight(), pdfimg.getWidth());
			}


			if (currentSection != null) {
				Paragraph p = new Paragraph();
				p.add(pdfimg);
				currentSection.add(p);
			} else if (cell != null) {
				cell.addElement(pdfimg);
			}

		}
	}

	private void renderFile(Section currentSection, PdfPCell cell) {
		Element e = get2InfoBox("Download", "Please check online to download the file");
		if (currentSection != null) {
			currentSection.add(e);
		} else if (cell != null) {
			cell.addElement(e);
		}
	}

	private void renderVideo(Section currentSection, PdfPCell cell) {
		Element e = get2InfoBox("Video", "Please check online to download the file");
		if (currentSection != null) {
			currentSection.add(e);
		} else if (cell != null) {
			cell.addElement(e);
		}
	}

	private void renderQuiz(Section currentSection, PdfPCell cell) {
		Element e = get2InfoBox("Quiz", "Please check online to download the file");
		if (currentSection != null) {
			currentSection.add(e);
		} else if (cell != null) {
			cell.addElement(e);
		}
	}


	private PdfPTable get2InfoBox(String title, String description) {
		PdfPTable table = new PdfPTable(1);

		Paragraph p = new Paragraph(title);
		PdfPCell cell = new PdfPCell(p);
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBackgroundColor(new Color(240, 240, 240));

		table.addCell(cell);

		p = new Paragraph(description);
		cell = new PdfPCell(p);
		cell.setBorderWidthTop(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingBottom(10);
		cell.setBackgroundColor(new Color(240, 240, 240));

		table.addCell(cell);

		table.setSpacingBefore(10);
		table.setSpacingAfter(10);
		return table;
	}


	private Collection<Element> getRichText(String html) throws IOException, DocumentException {


		StyleSheet styles = new StyleSheet();

		// TODO das scheint hier nicht zu funktionieren
		styles.loadTagStyle("ul", "indent", "10");
		styles.loadTagStyle("li", "leading", "14");


		java.util.List<Element> ret = new ArrayList<Element>();
		for (Object o : HTMLWorker.parseToList(new StringReader(html), styles)) {
			Paragraph p = new Paragraph();
			p.add(o);
			ret.add(p);

		}
		return ret;
	}

	/**
	 * Inner class to add a header and a footer.
	 */
	class HeaderFooter extends PdfPageEventHelper {
		Phrase header = null;

		public void setTitle(String title) {
			header = new Phrase(title, titleFont);
		}

		/** Alternating phrase for the header. */

		/**
		 * Current page number (will be reset for every chapter).
		 */
		int pagenumber;

		/**
		 * Increase the page number.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
		 *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onStartPage(PdfWriter writer, Document document) {
			pagenumber++;
		}

		public void onChapterEnd(PdfWriter writer, Document document,
								 float position) {
			/*
			drawLine(writer.getDirectContent(),

							document.left(), document.right(), position - 5);
			*/

		}

		/**
		 * Adds the header and the footer.
		 *
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
		 *com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {

			Rectangle rect = writer.getBoxSize("art");


			ColumnText.showTextAligned(writer.getDirectContent(),
											  Element.ALIGN_RIGHT, new Phrase(String.format("%d", pagenumber)),
											  rect.getRight(), rect.getBottom() - 18, 0);
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");//dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			ColumnText.showTextAligned(writer.getDirectContent(),
											  Element.ALIGN_RIGHT, new Phrase(strDate),
											  rect.getRight(), rect.getTop() + 10, 0);
			if (pagenumber != 1) {
				ColumnText.showTextAligned(writer.getDirectContent(),
												  Element.ALIGN_LEFT, header,
												  rect.getLeft(), rect.getTop() + 10, 0);

			}

		}

		public void drawLine(PdfContentByte cb, float x1, float x2, float y) {
			cb.moveTo(x1, y);
			cb.lineTo(x2, y);
			cb.stroke();
		}
	}

}

