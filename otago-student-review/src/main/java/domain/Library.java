/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author William Duggan
 */
public class Library {
    
    private String libraryId;
    private String name;
    private String description;
    private String reviews;

    public Library(String libraryId, String name, String description) {
        this.libraryId = libraryId;
        this.name = name;
        this.description = description;
//        this.reviews = reviews;
    }
    
    // Added empty Constructor can use above code but this is easier.
    public Library(){}

    
    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Library{" + "libraryId=" + libraryId + ", name=" + name + ", description=" + description + ", reviews=" + reviews + '}';
    }
    
}
