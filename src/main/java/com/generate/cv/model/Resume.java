package com.generate.cv.model;

import java.util.ArrayList;
import java.util.Map;

public class Resume {

    private Header header;
    private JobAppliedFor jobAppliedFor;
    private ArrayList<Education> education;
    private ArrayList<Experience> experience;
    private Map<String, String> skills;
    private ArrayList<Project> projects;
//    private ArrayList<Language> languages;

//    public ArrayList<Language> getLanguages() {
//        return languages;
//    }
//
//    public void setLanguages(ArrayList<Language> languages) {
//        this.languages = languages;
//    }

    public ArrayList<Education> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<Education> education) {
        this.education = education;
    }

    public ArrayList<Experience> getExperience() {
        return experience;
    }

    public void setExperience(ArrayList<Experience> experience) {
        this.experience = experience;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Map<String, String> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, String> skills) {
        this.skills = skills;
    }

    public JobAppliedFor getJobAppliedFor() {
        return jobAppliedFor;
    }

    public void setJobAppliedFor(JobAppliedFor jobAppliedFor) {
        this.jobAppliedFor = jobAppliedFor;
    }

    @Override
    public String toString() {
        return "Resume [header=" + header + ", education=" + education + ", experience=" + experience + ", skills="
                + skills + ", projects=" + projects + "]";
    }

}