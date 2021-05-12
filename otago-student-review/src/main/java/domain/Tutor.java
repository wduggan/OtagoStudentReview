/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author caothihoangngan
 */
public class Tutor {

    private String tutor_ID;

    private String name;
    private String email;
    private ArrayList<String> paperList = new ArrayList<>();
    private String description;
    private String review;

    public Tutor(String tutor_ID, String name, String email, ArrayList<String> paperList, String description, String review) {
        this.tutor_ID = tutor_ID;
        this.name = name;
        this.email = email;
        this.paperList = paperList;
        this.description = description;
        this.review = review;
    }

    public Tutor() {
    }

    
    
    
    public String getTutor_ID() {
        return tutor_ID;
    }

    public void setTutor_ID(String tutor_ID) {
        this.tutor_ID = tutor_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPaper() {
        return paperList;
    }

    public void setPaper(ArrayList<String> paper) {
        this.paperList = paper;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Tutor{" + "tutor_ID=" + tutor_ID + ", name=" + name + ", email=" + email + ", paperList=" + paperList + ", description=" + description + ", review=" + review + '}';
    }

}
