package com.generate.cv.util;

import com.generate.cv.model.*;
import com.generate.cv.model.Header;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.Rectangle;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@Component
public class PdfOneGenerator {

    private static final Logger log = Logger.getLogger(PdfOneGenerator.class);
    private static final int TABLE1STPARAM = 3;
    private static final int TABLE2NDPARAM = 8;
    private static final int PADDINGSIZE = 10;
    /* Fonts for lines */
    private static final Font nameStyle = new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD);
    private static final Font emptyLineStyle = new Font(Font.FontFamily.HELVETICA, 25f, Font.BOLD);
    private static final Font headingStyle = new Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD);
    private static final Font contactStyle = new Font(Font.FontFamily.HELVETICA, 11f, Font.ITALIC);
    private static final Font contentStyle = new Font(Font.FontFamily.HELVETICA, 9f);
    private static final BaseColor europassBaseColor = new BaseColor(50, 91, 168);

    {
        contentStyle.setColor(50, 91, 168);
        nameStyle.setColor(50, 91, 168);
        headingStyle.setColor(50, 91, 168);
        contactStyle.setColor(50, 91, 168);
    }

    public String createDocument(Resume resume) throws IOException {
        Document document = new Document();
        try {
            String file_location = "target/" + resume.getHeader().getName() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(file_location)));
            log.info("==========Pdf document is opened============");
            document.open();
            if (resume != null) {
                if (resume.getHeader() != null) {
                    addMetaData(document, resume.getHeader().getName());
                    log.info("Adding meta data");
                }
                addLayoutConfig(document);
                log.info("Adding page layout configurations");
                addLines(writer);

                addLogo(document, resume.getHeader());
                log.info("Adding logo!");
                addEmptyLines(document);
                addEmptyLines(document);

                if (resume.getHeader() != null) {
                    addHeader(document, resume.getHeader());
                    log.info("Adding header details");
                }

                //job applied for
                if (resume.getJobAppliedFor() != null) {
                    addJobAppliedFor(document, resume.getJobAppliedFor());
                    log.info("Adding job applied for details");
                }

                if (resume.getExperience() != null) {
                    addExperience(document, resume.getExperience());
                    log.info("Adding professional experience");
                }

                if (resume.getEducation() != null) {
                    addEducation(document, resume.getEducation());
                    log.info("Adding educational data");
                }


                if (resume.getSkills() != null) {
                    addSkills(document, resume.getSkills()
//                            , resume.getLanguages()
                            );
                    log.info("Adding languages and skills");
                }

                if (resume.getProjects() != null) {

                    addProjects(document, resume.getProjects());
                    log.info("Adding projects section");
                }
            }
            document.close();
            writer.close();
            log.info("==========Pdf created successfully============");

            return "Success";
        } catch (Exception e) {
            // TODO: handle exception
            log.error("==========Error while creating pdf============");
            log.error(e.getMessage());
        }

        return "Failed";
    }

    public byte[] getDocument(String file_name) throws IOException {
        String file_location = "target\\" + file_name + ".pdf";
        System.out.println("---------------------------------------------");
        System.out.println(file_name);
        System.out.println("---------------------------------------------");
        File file = new File(file_location);

        if(!file.exists()){
            file.createNewFile();
            log.info("New file created!");
        }else{
            log.info("File already exists");
        }

        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(bytesArray);
            fis.close();
            return bytesArray;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("File not found!");
            log.info("Recreating file...");
            file = new File(file_location);
            bytesArray = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            fis.read(bytesArray);
            fis.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static void addMetaData(Document document, String author) {
        document.addTitle("My Resume PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor(author);
        document.addCreator("BuildByResume.com");
    }

    public static void addLayoutConfig(Document document) {
        /* Margins */
        document.setPageSize(PageSize.A4);
        document.setMargins(50f, 44f, 69f, 69f);
    }

    public static void addEmptyLines(Document document) throws DocumentException {
        Paragraph emptyLine = new Paragraph(" ", emptyLineStyle);
        document.add(emptyLine);
    }

    public static void addLogo(Document document, Header header) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{3, 5, 3});
        table.setPaddingTop(30f);
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100f);

//        com.itextpdf.text.Image image = null;
//        try {
//            resize("C:\\Users\\U742551\\Desktop\\projekte\\cv\\cv\\src\\main\\file\\europass_logo.jpg", 91, 50);
//            image = com.itextpdf.text.Image.getInstance("C:\\Users\\U742551\\Desktop\\projekte\\cv\\cv\\src\\main\\file\\europass_logo.jpg");
//        } catch (IOException | BadElementException e) {
//            e.printStackTrace();
//        }

        PdfPCell logoCell = new PdfPCell(new Phrase(" "));
        logoCell.setPaddingRight(PADDINGSIZE);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
        logoCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(logoCell);

        PdfPCell emptyCell = new PdfPCell(new Phrase("Curriculum Vitae", contentStyle));
        emptyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        emptyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        emptyCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(emptyCell);

        PdfPCell nameCell = new PdfPCell(new Phrase(header.getName(), contentStyle));
        nameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        nameCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(nameCell);

        document.add(table);
    }

    public static void addHeader(Document document, Header header) {
        try {
//            Paragraph title = new Paragraph(header.getName() + "\n", nameStyle);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//
//            addEmptyLines(document);

            PdfPTable table = new PdfPTable(new float[]{3, 8});
            table.setPaddingTop(30f);
            table.getDefaultCell().setBorder(10);
            table.setWidthPercentage(100f);

            com.itextpdf.text.Image image = null;
            try {
                resize("src\\main\\file\\messi.jpg", 91, 106);
                image = com.itextpdf.text.Image.getInstance("src\\main\\file\\messi.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }


            PdfPCell personalInfo = new PdfPCell(new Phrase("Personal Information".toUpperCase(), contentStyle));
            personalInfo.setPaddingRight(PADDINGSIZE);
            personalInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
            personalInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            personalInfo.setBorder(Rectangle.NO_BORDER);
            table.addCell(personalInfo);

            PdfPCell nameCell = new PdfPCell(new Phrase(header.getName(), contentStyle));
            nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            nameCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(nameCell);

            PdfPCell emptyCell = new PdfPCell();
            Paragraph emptyParagraph = new Paragraph(" ");
            emptyParagraph.setAlignment(Element.ALIGN_LEFT);
            emptyCell.addElement(emptyParagraph);
            emptyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            emptyCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(emptyCell);

            PdfPCell empty2Cell = new PdfPCell();
            Paragraph empty2Paragraph = new Paragraph(" ");
            empty2Paragraph.setAlignment(Element.ALIGN_LEFT);
            empty2Cell.addElement(empty2Paragraph);
            empty2Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            empty2Cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(empty2Cell);

            PdfPCell imageCell = new PdfPCell(image, false);
            imageCell.setPaddingRight(PADDINGSIZE);
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            imageCell.setRowspan(6);
            imageCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(imageCell);

            PdfPCell addressCell = new PdfPCell(new Phrase(header.getAddress(), contentStyle));
            addressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            addressCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            addressCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(addressCell);

            PdfPCell personalWebsite = new PdfPCell(new Phrase(header.getWebsite(), contentStyle));
            personalWebsite.setHorizontalAlignment(Element.ALIGN_LEFT);
            personalInfo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            personalWebsite.setBorder(Rectangle.NO_BORDER);
            table.addCell(personalWebsite);

            PdfPCell emailCell = new PdfPCell(new Phrase(header.getEmailAddress(), contentStyle));
            emailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            emailCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(emailCell);

            PdfPCell phoneCell = new PdfPCell(new Phrase(header.getPhoneNumber(), contentStyle));
            phoneCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phoneCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            phoneCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(phoneCell);

            PdfPCell githubCell = new PdfPCell(new Phrase(header.getGithub(), contentStyle));
            githubCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            githubCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            githubCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(githubCell);

            PdfPCell linkedInCell = new PdfPCell(new Phrase(header.getLinkedin(), contentStyle));
            linkedInCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            linkedInCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            linkedInCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(linkedInCell);

            document.add(table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addJobAppliedFor(Document document, JobAppliedFor jobAppliedFor) {

        try {
            PdfPTable table = new PdfPTable(new float[]{3, 8});
            table.setPaddingTop(30f);
            table.getDefaultCell().setBorder(10);
            table.setWidthPercentage(100f);

            PdfPCell jobLabel = new PdfPCell(new Phrase("JOB APPLIED FOR\n" +
                    "POSITION\n" +
                    "PREFERRED JOB\n" +
                    "STUDIES APPLIED FOR\n" +
                    "PERSONAL STATEMENT", contentStyle));
            jobLabel.setPaddingRight(PADDINGSIZE);
            jobLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            jobLabel.setBorder(Rectangle.NO_BORDER);
            table.addCell(jobLabel);

            PdfPCell jobCell = new PdfPCell(new Phrase(jobAppliedFor.getJobName(), contentStyle));
            jobCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            jobCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(jobCell);

            document.add(table);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void addLines(PdfWriter writer) {

        /*
         * PdfContentByte canvas = writer.getDirectContent();
         * canvas.setColorStroke(BaseColor.BLACK ); canvas.moveTo(35, 735);
         * canvas.lineTo(560, 735); canvas.closePathStroke();
         *
         * canvas.setColorStroke(BaseColor.BLACK ); canvas.moveTo(35, 610);
         * canvas.lineTo(560, 610); canvas.closePathStroke();
         */
    }

    public static void addEducation(Document document, ArrayList<Education> education) throws DocumentException {
        PdfPTable tablehead = new PdfPTable(new float[]{100f});
        tablehead.getDefaultCell().setBorder(0);
        tablehead.setWidthPercentage(100f);

        PdfPCell headCell = new PdfPCell();
        Paragraph title = new Paragraph("Education", nameStyle);
        headCell.addElement(title);
        headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headCell.setBorder(Rectangle.BOTTOM);
        headCell.setBorderColor(new BaseColor(50, 91, 168));
        headCell.setBorderWidth(1f);
        tablehead.addCell(headCell);

        document.add(tablehead);
        addEmptyLines(document);

        PdfPTable table = new PdfPTable(new float[]{3, 8});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100f);

        for (Education edu : education) {
            PdfPCell collegeTimeCell = new PdfPCell();
            collegeTimeCell.setPaddingRight(PADDINGSIZE);
            Paragraph colTime = new Paragraph(edu.getPeriod(), headingStyle);
            colTime.setAlignment(Element.ALIGN_RIGHT);
//            Paragraph colGPA = new Paragraph("GPA: " + edu.getGpa(), contactStyle);
//            colGPA.setAlignment(Element.ALIGN_RIGHT);
            collegeTimeCell.addElement(colTime);
//            collegeTimeCell.addElement(colGPA);
            collegeTimeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            collegeTimeCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(collegeTimeCell);

            PdfPCell collegeCell = new PdfPCell();
            Paragraph colName = new Paragraph(edu.getDegree() + " | " + edu.getName(), headingStyle);
            Paragraph colPlace = new Paragraph(edu.getLocation(), contactStyle);
            Paragraph colCourse = new Paragraph(edu.getMajors(), contactStyle);
            collegeCell.addElement(colName);
            collegeCell.addElement(colPlace);
            collegeCell.addElement(colCourse);
            collegeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            collegeCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(collegeCell);


        }
        document.add(table);
        addEmptyLines(document);

    }

    public static void addExperience(Document document, ArrayList<Experience> experiences) throws DocumentException {
        PdfPTable tablehead = new PdfPTable(new float[]{100f});
        tablehead.getDefaultCell().setBorder(0);
        tablehead.setWidthPercentage(100f);
        tablehead.setSkipFirstHeader(true);
        tablehead.setSkipLastFooter(true);

        PdfPCell headCell = new PdfPCell();
        Paragraph title = new Paragraph("Experiences", nameStyle);
        headCell.addElement(title);
        headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headCell.setBorder(Rectangle.BOTTOM);
        headCell.setBorderColor(europassBaseColor);
        headCell.setBorderWidth(1f);
        tablehead.addCell(headCell);

        document.add(tablehead);
        addEmptyLines(document);
        for (Experience exp : experiences) {
            /* header */
            PdfPTable table = new PdfPTable(new float[]{3, 8});
            table.getDefaultCell().setBorder(0);
            table.setWidthPercentage(100f);


            PdfPCell jobTimeCell = new PdfPCell();
            jobTimeCell.setPaddingRight(PADDINGSIZE);
            Paragraph jobTime = new Paragraph(exp.getPeriod(), headingStyle);
            jobTime.setAlignment(Element.ALIGN_RIGHT);
            jobTimeCell.addElement(jobTime);
            jobTimeCell.setBorder(0);
            table.addCell(jobTimeCell);


            PdfPCell jobCell = new PdfPCell();
            Paragraph job = new Paragraph(exp.getJobrole() + " at " + exp.getCompany() + " " + exp.getLocation(), headingStyle);
            jobCell.addElement(job);
            jobCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            jobCell.setBorder(0);
            table.addCell(jobCell);

            Paragraph emptyParagraph = new Paragraph(" ");
            emptyParagraph.setAlignment(Element.ALIGN_LEFT);
            PdfPCell emptyCell;
            PdfPCell resCell;

            for (String res : exp.getResponsibilites()) {
                emptyCell = new PdfPCell();
                emptyCell.addElement(emptyParagraph);
                emptyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                emptyCell.setBorder(0);
                table.addCell(emptyCell);

                resCell = new PdfPCell(new Phrase("-" + res, contentStyle));
                resCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                resCell.setBorder(0);
                table.addCell(resCell);
            }
            document.add(table);
        }
    }

    public static void addSkills(Document document, Map<String, String> skill
//            , ArrayList<Language> languages
    ) throws DocumentException {
        PdfPTable tablehead = new PdfPTable(new float[]{100f});
        tablehead.getDefaultCell().setBorder(0);
        tablehead.setWidthPercentage(100f);

        PdfPCell headCell = new PdfPCell();
        Paragraph title = new Paragraph("PERSONAL SKILLS", nameStyle);
        headCell.addElement(title);
        headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headCell.setBorder(Rectangle.BOTTOM);
        headCell.setBorderWidth(1f);
        headCell.setBorderColor(europassBaseColor);
        tablehead.addCell(headCell);

        document.add(tablehead);
        addEmptyLines(document);

        PdfPTable languagesTable = new PdfPTable(new float[]{TABLE1STPARAM, TABLE2NDPARAM});
        languagesTable.getDefaultCell().setBorder(0);
        languagesTable.setWidthPercentage(100f);

        PdfPCell otherLanguagesLabelCell = new PdfPCell(new Phrase("Other languages", contentStyle));
        otherLanguagesLabelCell.setPaddingRight(PADDINGSIZE);
        otherLanguagesLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        otherLanguagesLabelCell.setBorder(Rectangle.NO_BORDER);
        languagesTable.addCell(otherLanguagesLabelCell);

        PdfPCell test2 = new PdfPCell();

        PdfPTable staticLanguageHeadingTable = new PdfPTable(new float[]{1,1,1,1,1});
        staticLanguageHeadingTable.getDefaultCell().setBorder(1);
        staticLanguageHeadingTable.setWidthPercentage(100f);

        PdfPCell understandingCell = new PdfPCell(new Phrase("UNDERSTADING", contentStyle));
        understandingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        understandingCell.setBorder(15);
        understandingCell.setBorderWidthLeft(0);
        understandingCell.setColspan(2);
        staticLanguageHeadingTable.addCell(understandingCell);

        PdfPCell speakingCell = new PdfPCell(new Phrase("SPEAKING", contentStyle));
        speakingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        speakingCell.setBorder(15);
        speakingCell.setColspan(2);
        staticLanguageHeadingTable.addCell(speakingCell);

        PdfPCell writingCell = new PdfPCell(new Phrase("WRITING", contentStyle));
        writingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        writingCell.setBorder(15);
        writingCell.setBorderWidthRight(0);
        staticLanguageHeadingTable.addCell(writingCell);

        PdfPCell listeningCell = new PdfPCell(new Phrase("Listening", contentStyle));
        listeningCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        listeningCell.setBorder(15);
        listeningCell.setBorderWidthLeft(0);
        staticLanguageHeadingTable.addCell(listeningCell);

        PdfPCell readingCell = new PdfPCell(new Phrase("Reading", contentStyle));
        readingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        readingCell.setBorder(15);
        staticLanguageHeadingTable.addCell(readingCell);

        PdfPCell spokenCell1 = new PdfPCell(new Phrase("Spoken interaction", contentStyle));
        spokenCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        spokenCell1.setBorder(15);
        staticLanguageHeadingTable.addCell(spokenCell1);

        PdfPCell spokenCell2 = new PdfPCell(new Phrase("Spoken production", contentStyle));
        spokenCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        spokenCell2.setBorder(15);
        staticLanguageHeadingTable.addCell(spokenCell2);

        PdfPCell emptyCell = new PdfPCell(new Phrase(" ", contentStyle));
        emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        emptyCell.setBorder(15);
        staticLanguageHeadingTable.addCell(emptyCell);

        test2.addElement(staticLanguageHeadingTable);

        languagesTable.addCell(test2);


//        for (Language language : languages){
//
//            PdfPCell languageLabelCell = new PdfPCell(new Phrase(language.getLanguageName(), contentStyle));
//            languageLabelCell.setPaddingRight(PADDINGSIZE);
//            languageLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            languageLabelCell.setBorder(Rectangle.NO_BORDER);
//            languagesTable.addCell(languageLabelCell);
//
//
//            PdfPTable languageLevel = new PdfPTable(new float[]{1,1,1,1,1});
//            languageLevel.getDefaultCell().setBorder(1);
//            languageLevel.setWidthPercentage(100f);
//
//            PdfPCell listeningLevelCell = new PdfPCell(new Phrase(language.getListeningLevel(), contentStyle));
//            listeningLevelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            listeningLevelCell.setBorder(15);
//            languageLevel.addCell(listeningLevelCell);
//
//            PdfPCell readingLevelCell = new PdfPCell(new Phrase(language.getReadingLevel(), contentStyle));
//            readingLevelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            readingLevelCell.setBorder(15);
//            languageLevel.addCell(readingLevelCell);
//
//            PdfPCell spokenInteractionLevelCell = new PdfPCell(new Phrase(language.getSpokenInteractionLevel(), contentStyle));
//            spokenInteractionLevelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            spokenInteractionLevelCell.setBorder(15);
//            languageLevel.addCell(spokenInteractionLevelCell);
//
//            PdfPCell spokenProductionLevelCell = new PdfPCell(new Phrase(language.getSpokenProductionLevel(), contentStyle));
//            spokenProductionLevelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            spokenProductionLevelCell.setBorder(15);
//            languageLevel.addCell(spokenProductionLevelCell);
//
//            PdfPCell writingLevelCell = new PdfPCell(new Phrase(language.getWritingLevel(), contentStyle));
//            writingLevelCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            writingLevelCell.setBorder(15);
//            languageLevel.addCell(writingLevelCell);
//
//            PdfPCell certificateCell = new PdfPCell(new Phrase("certification", contentStyle));
//            certificateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            certificateCell.setBorder(15);
//            certificateCell.setColspan(5);
//            languageLevel.addCell(certificateCell);
//
//            PdfPCell testCell = new PdfPCell();
//            testCell.setHorizontalAlignment(Rectangle.RIGHT);
//            testCell.addElement(languageLevel);
//
//            languagesTable.addCell(testCell);
//        }


        document.add(languagesTable);
        addEmptyLines(document);
        addEmptyLines(document);


        PdfPTable tableTest = new PdfPTable(new float[]{TABLE1STPARAM, TABLE2NDPARAM});
        tableTest.getDefaultCell().setBorder(0);
        tableTest.setWidthPercentage(100f);

        PdfPCell labelCell = new PdfPCell(new Phrase("Skills", contentStyle));
        labelCell.setPaddingRight(PADDINGSIZE);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        labelCell.setBorder(Rectangle.NO_BORDER);
        tableTest.addCell(labelCell);

        java.util.List<String> skills = new ArrayList<>();
        for (Map.Entry<String, String> me : skill.entrySet()) {
            skills.add(me.getValue());
        }

        PdfPCell skillsCell = new PdfPCell();
        List list = new List(List.UNORDERED);
        list.setListSymbol("•");
        for (String s : skills) {
            ListItem item = new ListItem(s, contentStyle);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
        }
        skillsCell.addElement(list);
        skillsCell.setBorder(Rectangle.NO_BORDER);
        tableTest.addCell(skillsCell);
        document.add(tableTest);
    }

    public static void addProjects(Document document, ArrayList<Project> projectList) throws DocumentException {
        PdfPTable tablehead = new PdfPTable(new float[]{100f});
        tablehead.getDefaultCell().setBorder(0);
        tablehead.setWidthPercentage(100f);

        PdfPCell headCell = new PdfPCell();
        Paragraph title = new Paragraph("Projects", nameStyle);
        headCell.addElement(title);
        headCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headCell.setBorder(Rectangle.BOTTOM);
        headCell.setBorderWidth(1f);
        headCell.setBorderColor(europassBaseColor);
        tablehead.addCell(headCell);

        document.add(tablehead);
        addEmptyLines(document);

        PdfPTable table = new PdfPTable(new float[]{3, 8});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100f);

        for (Project pro : projectList) {

            PdfPCell projectNameCell = new PdfPCell();
            projectNameCell.setPaddingRight(PADDINGSIZE);
            Paragraph proName = new Paragraph(pro.getName(), headingStyle);
            proName.setAlignment(Element.ALIGN_RIGHT);
            projectNameCell.addElement(proName);
            projectNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            projectNameCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(projectNameCell);

            PdfPCell descCell = new PdfPCell();
            Paragraph proDesc = new Paragraph(pro.getDescription(), contentStyle);
            Paragraph proTech = new Paragraph("Technologies: " + pro.getTechnology(), contentStyle);
            descCell.addElement(proDesc);
            descCell.addElement(proTech);
            descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            descCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(descCell);

            document.add(table);
        }
    }

    public static void resize(String inputImagePath, int scaledWidth, int scaledHeight) throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = inputImagePath.substring(inputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(inputImagePath));
    }
}