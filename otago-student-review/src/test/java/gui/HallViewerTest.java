/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HallDAO;
import domain.Hall;
import gui_helpers.SimpleListModel;
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
public class HallViewerTest {
    
    private HallDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    
    private Hall hall1;
    private Hall hall2;
    private Hall hall3;
    
     @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(150);
        
        // create a mock for the DAO
        dao = mock(HallDAO.class);
        
        Collection<Hall> halls = new HashSet<>();
        hall1 = new Hall("1", "Arana Coll", "Arana College is a residential college of the University of Otago in Dunedin, New Zealand, \nfounded in 1943 by the Rev. William Turner and the Stuart Residence Halls Council.");
        hall2 = new Hall("2", "Unicol", "University College, founded in 1969, is the largest \nresidential hall at the University of Otago in Dunedin, New Zealand.");
        hall3 = new Hall("3", "Selwyn College", "Selwyn College is a residential college affiliated \nto the University of Otago in Dunedin, New Zealand.");
        halls.add(hall1);
        halls.add(hall2);
        halls.add(hall3);
        
        when(dao.getHalls()).thenReturn(halls);
        
        
        //stub the delete method
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // remove the library from the collection that getProducts() uses
                halls.remove(hall3);
                return null;
            }
        }).when(dao).removeHall(hall3);

    }
    
     @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }
    
    
    @Test
    public void testViewHalls() {

        HallsViewer dialog = new HallsViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        verify(dao).getHalls();

        // get the model
        SimpleListModel model = (SimpleListModel) fixture.list("lstHalls").target().getModel();

        // check the contents
        assertTrue("list contains the expected hall", model.contains(hall1));
        assertTrue("list contains the expected hall", model.contains(hall2));
        assertTrue("list contains the expected hall", model.contains(hall3));
        assertEquals("list contains the correct number of halls", 3, model.getSize());

        fixture.close();

    }
    
    
   
    
    
    
    
     @Test
    public void testEdit() {

        HallsViewer dialog = new HallsViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        // select item to edit
        fixture.list("lstHalls").selectItem(hall1.toString());

        // click the edit button
        fixture.button("btnEdit").click();

        // get the hall editor dialog that appears (which is the dialog that currently has focus)
        DialogFixture editDialog = fixture.focus();

        // verify that the UI componenents contains the hall details
        editDialog.textBox("txtHallID").requireText(hall1.getHallId());
        editDialog.textBox("txtHallName").requireText(hall1.getTitle());
        editDialog.textBox("txtDescription").requireText(hall1.getDescription());

        // edit the name and description
        editDialog.textBox("txtHallName").selectAll().deleteText().enterText("Arana College");
        editDialog.textBox("txtDescription").selectAll().deleteText().enterText("Arana College updated description test.");

        // click the save button
        editDialog.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed hall from the mocked DAO
        ArgumentCaptor<Hall> argument = ArgumentCaptor.forClass(Hall.class);

        // verify that the DAO.save method was called, and capture the passed hall
        verify(dao).saveHall(argument.capture());

        // retrieve the passed hall from the captor
        Hall editedHall = argument.getValue();

        // check that the changes were saved
        assertThat("Ensure the name was changed", editedHall.getTitle(), is("Arana College"));
        assertThat("Ensure the description was changed", editedHall.getDescription(), is("Arana College updated description test."));

    }
    
    
    @Test
    public void testDeleteHall() {

        HallsViewer dialog = new HallsViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // select item to edit
        fixture.list("lstHalls").selectItem(hall3.toString());

        // click the edit button
        fixture.button("btnDelete").click();

        DialogFixture confirm = fixture.focus();

        confirm.button(withText("Yes")).click();
        //confirm.close();

        // create a Mockito argument captor to use to retrieve the passed library from the mocked DAO
        ArgumentCaptor<Hall> argument = ArgumentCaptor.forClass(Hall.class);

        // verify that the DAO.save method was called, and capture the passed hall
        verify(dao).removeHall(argument.capture());


        SimpleListModel model = (SimpleListModel) fixture.list("lstHalls").target().getModel();

        assertTrue("list does not contain deleted library", !(model.contains(hall3)));
        assertTrue("list contains library that remains", model.contains(hall1));
        assertTrue("list contains library that remains", model.contains(hall2));
        fixture.close();

    }
    
    
}
