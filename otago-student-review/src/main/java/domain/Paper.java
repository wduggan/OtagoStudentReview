package domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author William Duggan
 */
public class Paper {

    private String paperId;
    private String code;
    private String title;
    private String description;
    private String reviews;
    private String department;


    public Paper(String paperId, String code, String title, String description, String reviews, String department) {
        this.paperId = paperId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.reviews = reviews;
        this.department = department;
    }
    // Added Constructor can use above code but this is easier.

    public Paper() {
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Paper{" + "paperId=" + paperId + ", title=" + title + ", code=" + code + ", description=" + description + ", reviews=" + reviews + ", department=" + department + '}';
    }

}
