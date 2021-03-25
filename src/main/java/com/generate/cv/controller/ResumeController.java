package com.generate.cv.controller;

import com.generate.cv.config.AppConfig;
import com.generate.cv.model.Resume;
import com.generate.cv.util.PdfOneGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api")
public class ResumeController {

    private static final Logger log = Logger.getLogger(ResumeController.class);

    @Autowired
    private PdfOneGenerator pdfGen;

    private AppConfig appConfig;

    @PostMapping(path = "/resume")
    public ResponseEntity<String> postResume(@Valid @RequestBody Resume resume) throws IOException {

        log.info(resume.getHeader());
        log.info(resume.getJobAppliedFor());
        log.info(resume.getExperience());
        log.info(resume.getEducation());
        log.info(resume.getProjects());
        log.info(resume.getSkills());
//        log.info(resume.getLanguages());
        return new ResponseEntity<String>(pdfGen.createDocument(resume), HttpStatus.OK);
    }

    @GetMapping(path = "/resumef")
    public ResponseEntity<byte[]> getResume(@RequestParam("filename") String filename) throws IOException {

        return new ResponseEntity<byte[]>(pdfGen.getDocument(filename), HttpStatus.OK);
    }
}
