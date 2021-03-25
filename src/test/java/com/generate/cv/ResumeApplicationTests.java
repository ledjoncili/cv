package com.generate.cv;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.generate.cv.model.*;
import com.generate.cv.util.PdfOneGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResumeApplicationTests {

	@Test
	public void contextLoads() {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("target/test.pdf")));
			System.out.println("==========Pdf document is opened============");
			Header header=new Header();
			header.setName("Ledjon Cili");
			header.setAddress("Berat AL");
			header.setPhoneNumber("+355 68 87 16 906");
			header.setEmailAddress("ledjoncilih@gmail.com");
			header.setWebsite("---------------");
			header.setLinkedin("linkedin.com/in/ledjoncili");
			header.setGithub("github.com/lcili");

			JobAppliedFor jobAppliedFor = new JobAppliedFor();
			jobAppliedFor.setJobName("Software Developer");

			Education ed1=new Education();
			ed1.setDegree("Masters");
			ed1.setGpa("3.7");
			ed1.setLocation("Charlotte, NC");
			ed1.setMajors("Computer Science");
			ed1.setName("University of North Carolina at Charlotte");
			ed1.setPeriod("Jan 2017 - May 2018");
			Education ed2=new Education();
			ed2.setDegree("Bachelors");
			ed2.setGpa("3.4");
			ed2.setLocation("Hyderabad India");
			ed2.setMajors("Electronics and Communication Engineering");
			ed2.setName("Mahatma Gandhi Institute of Technology");
			ed2.setPeriod("May 2011 - May 2015");

			ArrayList<Education> eduList=new ArrayList<>();
			eduList.add(ed1);
			eduList.add(ed2);


			ArrayList<Experience> expList=new ArrayList<>();

			Experience exp1=new Experience();
			exp1.setCompany("Investacorp");
			exp1.setJobrole("Java Developer");
			exp1.setLocation("Miami FL");
			exp1.setPeriod("Jan 2019- Present");

			ArrayList<String> resList=new ArrayList<String>();

			resList.add("Experience working in Agile development following Scrum process, Sprint and weekly standup meetings");
			resList.add("Developed DAOs using JPA, Hibernate Criteria API and also written complex HQL  and SQL queries ");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");
			resList.add("Configured and implemented Amazon Web Services such as SNS, SES and Ring Central API’s for notifications");
			resList.add("Performed Unit testing by implementing Junit test cases for ensuring code coverage");

			exp1.setResponsibilites(resList);

			Experience exp2=new Experience();
			exp2.setCompany("Digi Safari LLC");
			exp2.setJobrole("Software developer");
			exp2.setLocation("Alpharetta, GA");
			exp2.setPeriod("Aug 2018- Dec 2018");

			ArrayList<String> resList2=new ArrayList<String>();

			resList2.add("Developed Web application for Educational training website using Angular 6, Spring boot, Hibernate, SQL and AWS");
			resList2.add("Developed and Supported in the implementation of RESTful Services using Spring boot, Hibernate5 with JPA & SQL");
			resList2.add("Developed Reusable Angular Components using Typescript, NPM, HTML5 and Bootstrap");
			resList2.add("Developed and supported in building Angular Services integrating with REST API’s");
			resList2.add("Developed and supported in building Angular Services integrating with REST API’s");



			exp2.setResponsibilites(resList2);

			expList.add(exp1);
			expList.add(exp2);

			HashMap<String, String> map=new HashMap<String, String>();
			map.put("PROGRAMMING LANGUAGES", "Java 1.8, Python, R");
			map.put("FRAMEWORKS", "Spring, Spring Boot, Hibernate, JPA, Angular 6, React JS, Tapestry");
			map.put("SCRIPTING LANGUAGES", "Java Script, Type Script, PHP");
			map.put("DATABASE", "Oracle 11g, MySQL, Firebase, Mongo DB");
			map.put("WEB DEVELOPMENT", "HTML5, JSP, JSTL, jQuery, CSS3 & Bootstrap");

			ArrayList<Language> languages = new ArrayList<>();

			Language lang1 = new Language();
			lang1.setLanguageName("English");
			lang1.setListeningLevel("A1");
			lang1.setReadingLevel("A1");
			lang1.setSpokenInteractionLevel("A1");
			lang1.setSpokenProductionLevel("A1");
			lang1.setWritingLevel("A1");

			languages.add(lang1);

			Language lang2 = new Language();
			lang2.setLanguageName("Italian");
			lang2.setListeningLevel("A1");
			lang2.setReadingLevel("A1");
			lang2.setSpokenInteractionLevel("A1");
			lang2.setSpokenProductionLevel("A1");
			lang2.setWritingLevel("A1");

			languages.add(lang2);

			ArrayList<Project> prolist=new ArrayList<>();

			Project pro1=new Project();
			pro1.setDescription("Developed e-commerce web application by integrating RESTful Services build using Spring boot, hibernate and MySQL  with Front-end components build using Angular 6, Typescript  ");
			pro1.setName("E-Commerce ");
			pro1.setTechnology("Typescript, Angular 6 , Bootstrap 4 and Web services were implemented using Spring, Hibernate with JPA and MySQL");


			Project pro2=new Project();
			pro2.setDescription("Developed Mobile based Trip planner application for planning user trips more effectively");
			pro2.setName("Trip Planner");
			pro2.setTechnology("Java, Android Studio, Genymotion, Virtual Box, Firebase");

			prolist.add(pro1);
			prolist.add(pro2);

			document.open();
			PdfOneGenerator.addMetaData(document, header.getName());
			System.out.println("Adding meta data");

			PdfOneGenerator.addLayoutConfig(document);
			System.out.println("Adding page layout configurations");

			PdfOneGenerator.addLines(writer);

			PdfOneGenerator.addLogo(document, header);
			System.out.println("Adding europass logo!");
			PdfOneGenerator.addEmptyLines(document);

			PdfOneGenerator.addHeader(document, header);
			System.out.println("Adding header details");
			PdfOneGenerator.addEmptyLines(document);

			PdfOneGenerator.addJobAppliedFor(document, jobAppliedFor);
			System.out.println("Adding job applied for information!");

			PdfOneGenerator.addEducation(document, eduList);
			System.out.println("Adding educational data");

			PdfOneGenerator.addExperience(document, expList);
			System.out.println("Adding professional experience");

			PdfOneGenerator.addSkills(document, map
//					, languages
			);
			System.out.println("Adding skill section");

			PdfOneGenerator.addProjects(document, prolist);
			System.out.println("Adding projects section");

			document.close();
			writer.close();
			System.out.println("==========Pdf created successfully============");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("==========Error while creating pdf============");
		}

	}

}