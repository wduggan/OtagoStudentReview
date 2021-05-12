/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.PaperDAO;
import dao.TutorDAO;
import domain.Tutor;
import gui_helpers.SimpleListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author jesssmall
 */
public class TutorViewerTest {

    private TutorDAO dao;
    private PaperDAO paperDAO;
    private DialogFixture fixture;
    private DialogFixture fixture2;
    private Robot robot;

    private Tutor tutor1;
    private Tutor tutor2;
    private Tutor tutor3;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(150);

        

        ArrayList<String> papersList1 = new ArrayList<>();
        papersList1.add("COSC242");
        papersList1.add("COSC241");
        papersList1.add("INFO202");
        papersList1.add("INFO201");

        ArrayList<String> papersList2 = new ArrayList<>();
        papersList2.add("COMP160");
        papersList2.add("FINC102");
        
        ArrayList<String> papersList3 = new ArrayList<>();
        papersList3.add("LAWS102");
        papersList3.add("LAWS202");
        papersList3.add("LAWS205");

        // create a mock for the DAO
        dao = mock(TutorDAO.class);
        paperDAO = mock(PaperDAO.class);

        Collection<Tutor> tutors = new HashSet<>();
        tutor1 = new Tutor("1", "Jess Small", "jess@small.co.nz", papersList1, "Comp Sci Tutor");
        tutor2 = new Tutor("2", "Portia Sutherland", "portia@sutherland.co.nz", papersList2, "Math Tutor");
        tutor2 = new Tutor("3", "Bridget de Lautour", "b@dela.co.nz", papersList3, "Law Tutor");
        
        
        tutors.add(tutor1);
        tutors.add(tutor2);
        tutors.add(tutor3);
        
        //stub the getTutors method to return the test tutors
        when(dao.getTutors()).thenReturn(tutors);
        when(paperDAO.getPaperCodes()).thenReturn(papersList2);
        
        //stub the delete method
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // remove the tutor from the collection that getTutors() uses
                tutors.remove(tutor2);
                return null;
            }
        }).when(dao).removeTutor(tutor2);


    }
    
    @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }
    
    
    
     
    
     @Test
    public void testEdit() {
         TutorViewer dialog = new TutorViewer(null, true, dao, paperDAO);
         
         // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();
        
        // select item to edit
        fixture.list("lstTutor").selectItem(tutor2.toString());

        // click the edit button
        fixture.button("btnEdit").click();

        // get the tutor editor dialog that appears (which is the dialog that currently has focus)
        DialogFixture editDialog = fixture.focus();
        
        // verify that the UI componenents contains the tutors details
        editDialog.textBox("txtID").requireText(tutor2.getTutor_ID());
        editDialog.textBox("txtName").requireText(tutor2.getName());
        editDialog.textBox("txtEmail").requireText(tutor2.getEmail());
        editDialog.textBox("txtDescription").requireText(tutor2.getDescription());
        
        editDialog.textBox("txtName").selectAll().deleteText().enterText("Rosie Joyce");
        editDialog.list("listPapers").selectItems("COMP160");

        
        // click the save button
        editDialog.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed paper from the mocked DAO
        ArgumentCaptor<Tutor> argument = ArgumentCaptor.forClass(Tutor.class);

        // verify that the DAO.save method was called, and capture the passed paper
        verify(dao).saveTutor(argument.capture());

        // retrieve the passed paper from the captor
        Tutor editedTutor = argument.getValue();

        // check that the changes were saved
        assertThat("Ensure the name was changed", editedTutor.getName(), is("Rosie Joyce"));
        assertThat("Ensure the department was changed", editedTutor.getPaper(), contains("COMP160"));

    }
    
    
    @Test
    public void testViewTutors() {

        TutorViewer dialog = new TutorViewer(null, true, dao, paperDAO);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        verify(dao).getTutors();

        // get the model
        SimpleListModel model = (SimpleListModel) fixture.list("lstTutor").target().getModel();

        // check the contents
        assertTrue("list contains the expected tutor", model.contains(tutor1));
        assertTrue("list contains the expected tutor", model.contains(tutor2));
        assertTrue("list contains the expected tutor", model.contains(tutor3));
        assertEquals("list contains the correct number of tutors", 3, model.getSize());

        fixture.close();

    }
    
    
    
     @Test
    public void testDeleteTutor() {

        TutorViewer dialog = new TutorViewer(null, true, dao, paperDAO);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // select item to edit
        fixture.list("lstTutor").selectItem(tutor2.toString());

        // click the edit button
        fixture.button("btnDelete").click();

        DialogFixture confirm = fixture.focus();

        confirm.button(withText("Yes")).click();

        // create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
        ArgumentCaptor<Tutor>  argument = ArgumentCaptor.forClass(Tutor.class);

        // verify that the DAO.save method was called, and capture the passed paper
        verify(dao).removeTutor(argument.capture());


        SimpleListModel model = (SimpleListModel) fixture.list("lstTutor").target().getModel();

        assertTrue("list does not contain deleted tutor", !(model.contains(tutor2)));
        assertTrue("list contains product that remains", model.contains(tutor1));
        assertTrue("list contains product that remains", model.contains(tutor3));
        fixture.close();

    }


}
