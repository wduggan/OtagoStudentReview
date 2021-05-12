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
public class Hall {
    
    private String hallId;
    private String title;
    private String description;
//    private ArrayList<String> reviews = new ArrayList<String>();

    public Hall(String hallId, String title, String description) {
        this.hallId = hallId;
        this.title = title;
        this.description = description;
//        this.reviews = reviews;
    }
    
   // Added empty Constructor can use above code but this is easier.
   public Hall(){}


    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getReviews() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public void setReviews(ArrayList<String> reviews) {
    }

    @Override
    public String toString() {
        return "Hall{" + "hallId=" + hallId + ", title=" + title + ", description=" + description + ", reviews=" + "review" + '}';
    }

    
    
}
