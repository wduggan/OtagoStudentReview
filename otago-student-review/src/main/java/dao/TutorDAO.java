/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tutor;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author mattanderson
 */
public interface TutorDAO {

    Collection<Tutor> getTutors();

    ArrayList<String> getPapers();
    
    void removeTutorPaper(int index);

    void saveTutor(Tutor tutor);

    void removeTutor(Tutor tutor);

    Tutor searchByName(String name);

    Tutor searchByID(String id);

}
