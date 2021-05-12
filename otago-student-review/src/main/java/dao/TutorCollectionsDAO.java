/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author mattanderson
 */
public class TutorCollectionsDAO implements TutorDAO {

    private static final Collection<Tutor> tutors = new HashSet<>();
    private static final Map<String, Tutor> tutorsByID = new HashMap<>();
    private static final Map<String, Tutor> tutorsByName = new HashMap<>();
    private static final Map<ArrayList<String>, Tutor> papers = new HashMap<>();


    @Override
    public void saveTutor(Tutor tutor) {
        tutors.add(tutor);
        tutorsByID.put(tutor.getTutor_ID(), tutor);
        tutorsByName.put(tutor.getName(), tutor);
        papers.put(tutor.getPaper(), tutor);
    }

    @Override
    public Collection<Tutor> getTutors() {
        return tutors;
    }

    @Override
    public ArrayList<String> getPapers() {
        return (ArrayList<String>) papers;
    }

    @Override
    public Tutor searchByName(String name) {
        Tutor tutor = tutorsByName.get(name);
        if (tutor != null) {
            return tutor;
        } else {
            return null;
        }
    }

    @Override
    public Tutor searchByID(String id) {
        Tutor tutor = tutorsByID.get(id);
        if (tutor != null) {
            return tutor;
        } else {
            return null;
        }
    }
    
    
 

    /*  @Override
    public Tutor searchByName(String name) {
        if (tutorsByPaperId.containsKey(id)) {
            return tutorsByPaperId.get(id);
        } else {
            return null;
        }
    }*/
//    public Tutor getTutor(String tutor_ID) {
//        return tutors.get(tutor_ID);
//    }
//    public void removeTutor(Tutor tutor) {
//        tutor.remove(tutor);
//    }
    @Override
    public void removeTutor(Tutor tutor) {
        tutors.remove(tutor);
    }

    @Override
    public void removeTutorPaper(int index) {
        papers.remove(index);
    }
}
