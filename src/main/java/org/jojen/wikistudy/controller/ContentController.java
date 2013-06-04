package org.jojen.wikistudy.controller;

import org.apache.commons.io.FileUtils;
import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Image;
import org.jojen.wikistudy.entity.LearnContent;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.service.ContentService;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;
import org.jojen.wikistudy.util.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.File;
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

    protected static final Logger log = LoggerFactory
            .getLogger(ContentController.class);

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editContent(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "lessonid", required = false) Integer lessonid,
                              @RequestParam(value = "courseid", required = false) Integer courseid, Model model) {
        if (id == null) {
            model.addAttribute(new LearnContent());
        } else {
            model.addAttribute(contentService.findById(id));
        }
        model.addAttribute("lessonid", lessonid);
        model.addAttribute("courseid", courseid);
        model.addAttribute(new FileUpload());

        return "/content/content.edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editContent(@Valid LearnContent learnContent,
                              BindingResult bindingResult,
                              @RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "lessonid", required = true) Integer lessonid,
                              @RequestParam(value = "courseid", required = true) Integer courseid,
                              Model model) {
        log.debug("edit content={}", learnContent);

        LearnContent modelContent;
        Lesson l = null;
        if (id == null) {
            modelContent = new LearnContent();
            l = lessonService.findById(lessonid);
            l.addContent(modelContent);

        } else {
            modelContent = (LearnContent) contentService.findById(id);
        }

        // TODO hier vielleicht noch ein bisschen reflections
        modelContent.setName(learnContent.getName());
        modelContent.setText(learnContent.getText());
        contentService.update(modelContent);
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
                Content c = null;
                if (fileUpload.getFileData().getContentType().startsWith("image")) {
                    // TODO wir müssen das in einen store packen
                    File tempfile = File.createTempFile("file", ".tmp");
                    FileUtils.copyInputStreamToFile(fileUpload.getFileData().getInputStream(), tempfile);
                    Image image = new Image();
                    image.setName(fileUpload.getFileData().getName());
                    image.setPath(tempfile.getPath());
                    c = image;
                }

                if (c != null) {
                    contentService.insert(c);
                    l.addContent(c);
                    lessonService.update(l);
                }

            }
        } catch (IOException e) {
            log.error("Cannot process file", e);

        }
        return "json/boolean";
    }


}
