/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.PaperDAO;
import dao.TutorDAO;
import domain.Tutor;
import java.util.ArrayList;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author jesssmall
 */
public class TutorEditorTest {
    
    private TutorDAO dao;
    private PaperDAO paperDAO;
    private DialogFixture fixture;
    private Robot robot;
    
    
    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(40);
        ArrayList<String> papersList = new ArrayList<String>();

        
        papersList.add("COSC242");
        papersList.add("COSC241");
        papersList.add("INFO202");
        papersList.add("INFO201");
        
        
        // create mocks for the DAOs
        dao = mock(TutorDAO.class);
        paperDAO = mock(PaperDAO.class);

        // stub the getPaperCodes method to return the test papers
        when(paperDAO.getPaperCodes()).thenReturn(papersList);

    }
    
    @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }
    
    
    @Test
    public void testSave() {
        TutorEditor dialog = new TutorEditor(null, true, dao, paperDAO);

        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // click the dialog to ensure the robot is focused in the correct window
        fixture.click();

        //type in test data, and select chosen papers
        fixture.textBox("txtID").enterText("1");
        fixture.textBox("txtName").enterText("Jess Small");
        fixture.textBox("txtEmail").enterText("jess@small.co.nz");
        fixture.textBox("txtDescription").enterText("Test Description");
        fixture.list("listPapers").selectItems("COSC242", "COSC241");

        // click the save button
        fixture.button("btnSave").click();

        
        ArgumentCaptor<Tutor> argument = ArgumentCaptor.forClass(Tutor.class);


        // verify that the DAO.save method was called, and capture the passed tutor
        verify(dao).saveTutor(argument.capture());

        // retrieve the passed tutor from the captor
        Tutor savedTutor = argument.getValue();

        //ensure that all the correct data was saved properly
        assertThat("Ensure the ID was saved", savedTutor.getTutor_ID(), is("1"));
        assertThat("Ensure the name was saved", savedTutor.getName(), is("Jess Small"));
        assertThat("Ensure the email was saved", savedTutor.getEmail(), is("jess@small.co.nz"));
        assertThat("Ensure the description was saved", savedTutor.getDescription(), is("Test Description"));
        assertThat("Ensure the correct paper selection was made", savedTutor.getPaper(), contains("COSC242", "COSC241"));
        
    } 
    
}
