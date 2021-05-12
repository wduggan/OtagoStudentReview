/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Tutor;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jesssmall
 */
public class TutorCollectionsDAOTest {

    TutorDAO tutors = new TutorCollectionsDAO();
    private Tutor tutor1;
    private Tutor tutor2;
    private Tutor tutor3;
    private final ArrayList<String> papersList1 = new ArrayList<>();
    private final ArrayList<String> papersList2 = new ArrayList<>();
    private final ArrayList<String> papersList3 = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        //create test data

        tutor1 = new Tutor();
        tutor1.setTutor_ID("1");
        tutor1.setName("Jess Small");
        tutor1.setEmail("jess@small.co.nz");

        papersList1.add("COSC242");
        papersList1.add("COSC241");
        papersList1.add("INFO202");
        papersList1.add("INFO201");
        tutor1.setPaper(papersList1);

        tutor2 = new Tutor();
        tutor2.setTutor_ID("2");
        tutor2.setName("Rosie Joyce");
        tutor2.setEmail("rosie@joyce.co.nz");

        papersList2.add("COMP160");
        papersList2.add("COMP150");
        papersList2.add("COMP112");
        papersList2.add("MATH160");
        tutor2.setPaper(papersList2);

        tutor3 = new Tutor();
        tutor3.setTutor_ID("3");
        tutor3.setName("Shae Hattle");
        tutor3.setEmail("shae@hattle.co.nz");

        papersList3.add("FINC102");
        papersList3.add("BSNS113");
        papersList3.add("BSNS112");
        papersList3.add("PSYC111");
        tutor3.setPaper(papersList3);

        tutors.saveTutor(tutor1);
        tutors.saveTutor(tutor2);

    }

    @AfterEach
    public void tearDown() {
        tutors.removeTutor(tutor1);
        tutors.removeTutor(tutor2);
        tutors.removeTutor(tutor3);

    }

    //checks that the tutors have been added to the tutor collection
    @Test
    public void testSaveTutor() {
        assertThat(tutors.getTutors(), hasItem(tutor1));
        assertThat(tutors.getTutors(), hasItem(tutor2));
        assertThat(tutors.getTutors(), hasSize(2));

    }

    //Checks that deleting the tutor from the collection works correctly
    @Test
    public void testDeleteTutor() {
        tutors.removeTutor(tutor1);
        assertThat(tutors.getTutors(), not(hasItem(tutor1)));
        assertThat(tutors.getTutors(), hasSize(1));

    }

    
    //ensures that all papers are saved correctly to each tutor
    @Test
    public void testGetPapers() {
        assertThat(tutor1.getPaper(), hasSize(4));
        assertThat(tutor2.getPaper(), hasSize(4));
        assertThat(tutor3.getPaper(), hasSize(4));

        assertThat(tutor1.getPaper(), contains("COSC242", "COSC241", "INFO202", "INFO201"));
        assertThat(tutor2.getPaper(), contains("COMP160", "COMP150", "COMP112", "MATH160"));
        assertThat(tutor3.getPaper(), contains("FINC102", "BSNS113", "BSNS112", "PSYC111"));

    }

}
