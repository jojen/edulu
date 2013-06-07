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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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


    @RequestMapping(value = "/{type}/edit", method = RequestMethod.GET)
    public String editQuiz(@PathVariable(value = "type") String type,
                           @RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "lessonid", required = false) Integer lessonid,
                           @RequestParam(value = "courseid", required = false) Integer courseid, Model model) {
        if (id == null) {
            if (type.equals("text")) {
                model.addAttribute(new Text());
            } else if (type.equals("quiz")) {
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
                           BindingResult bindingResult,
                           @RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "lessonid", required = true) Integer lessonid,
                           @RequestParam(value = "courseid", required = true) Integer courseid,
                           Model model) {
        log.debug("edit quiz={}", quiz);

        Quiz modelQuiz;
        Lesson l = null;
        if (id == null) {
            modelQuiz = new Quiz();
            l = lessonService.findById(lessonid);
            l.addContent(modelQuiz);

        } else {
            modelQuiz = (Quiz) contentService.findById(id);
        }
        modelQuiz.setName("quiz 1");

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
        Lesson l = null;
        if (id == null) {
            modelText = new Text();
            l = lessonService.findById(lessonid);
            l.addContent(modelText);

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

                contentService.insert(b);
                blobService.save(fileUpload.getFileData(), b.getId());
                b.setContentType(fileUpload.getFileData().getContentType());
                b.setName(UriUtils.encodeQuery(fileUpload.getFileData().getFileItem().getName(), "UTF8"));

                contentService.update(b);
                l.addContent(b);
                lessonService.update(l);

            }
        } catch (Exception e) {
            log.error("cannot upload file", e);
        }

        return "json/boolean";
    }

    @RequestMapping(value = "/media/{id}/{name}", method = RequestMethod.GET)
    public void media(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        Content c = contentService.findById(id);
        if (c instanceof Blobbased) {
            Blobbased b = (Blobbased) c;
            File f = new File(blobService.get(b.getId()));
            response.setContentType(b.getContentType());
            response.setContentLength((int) (f.length() + 0));
            if (b instanceof Download) {
                response.setHeader("Content-Disposition", "attachment");
            }
            FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());


        }


    }


}
