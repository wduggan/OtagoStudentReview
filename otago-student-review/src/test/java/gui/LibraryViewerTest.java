/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.LibraryDAO;
import domain.Library;
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
public class LibraryViewerTest {
    
    private LibraryDAO dao;
    private DialogFixture fixture;
    private Robot robot;
    
    private Library library1;
    private Library library2;
    private Library library3;
    
    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(150);
        
        // create a mock for the DAO
        dao = mock(LibraryDAO.class);
        
        //create some test data
        Collection<Library> libraries = new HashSet<>();
        library1 = new Library("1", "Central Library", "Main library of Otago University");
        library2 = new Library("2", "Law Library", "Libary for law students");
        library3 = new Library("3", "Robertson Library", "Library for polytech and uni students");
        libraries.add(library1);
        libraries.add(library2);
        libraries.add(library3);
        
        //stub the getLibraries method
        when(dao.getLibraries()).thenReturn(libraries);
        
        
        //stub the delete method
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // remove the library from the collection that getProducts() uses
                libraries.remove(library3);
                return null;
            }
        }).when(dao).removeLibrary(library3);

    }
    
     @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }
    
    
    @Test
    public void testViewLibraries() {

        LibraryViewer dialog = new LibraryViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        verify(dao).getLibraries();

        // get the model
        SimpleListModel model = (SimpleListModel) fixture.list("lstLibrary").target().getModel();

        // check the contents
        assertTrue("list contains the expected library", model.contains(library1));
        assertTrue("list contains the expected library", model.contains(library2));
        assertTrue("list contains the expected library", model.contains(library3));
        assertEquals("list contains the correct number of libraries", 3, model.getSize());

        fixture.close();

    }
    
     @Test
    public void testEdit() {

        LibraryViewer dialog = new LibraryViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        // select item to edit
        fixture.list("lstLibrary").selectItem(library1.toString());

        // click the edit button
        fixture.button("btnEdit").click();

        // get the library editor dialog that appears (which is the dialog that currently has focus)
        DialogFixture editDialog = fixture.focus();

        // verify that the UI componenents contains the library details
        editDialog.textBox("txtLibraryID").requireText(library1.getLibraryId());
        editDialog.textBox("txtName").requireText(library1.getName());
        editDialog.textBox("txtDescription").requireText(library1.getDescription());

        // edit the name and description
        editDialog.textBox("txtName").selectAll().deleteText().enterText("Central Library Updated");
        editDialog.textBox("txtDescription").selectAll().deleteText().enterText("Central Library updated description test.");

        // click the save button
        editDialog.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed library from the mocked DAO
        ArgumentCaptor<Library> argument = ArgumentCaptor.forClass(Library.class);

        // verify that the DAO.save method was called, and capture the passed library
        verify(dao).saveLibrary(argument.capture());

        // retrieve the passed library from the captor
        Library editedLibrary = argument.getValue();

        // check that the changes were saved
        assertThat("Ensure the name was changed", editedLibrary.getName(), is("Central Library Updated"));
        assertThat("Ensure the description was changed", editedLibrary.getDescription(), is("Central Library updated description test."));

    }
    
    
    @Test
    public void testDeleteLibrary() {

        LibraryViewer dialog = new LibraryViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // select item to edit
        fixture.list("lstLibrary").selectItem(library3.toString());

        // click the edit button
        fixture.button("btnDelete").click();

        DialogFixture confirm = fixture.focus();

        confirm.button(withText("Yes")).click();
        //confirm.close();

        // create a Mockito argument captor to use to retrieve the passed library from the mocked DAO
        ArgumentCaptor<Library> argument = ArgumentCaptor.forClass(Library.class);

        // verify that the DAO.save method was called, and capture the passed library
        verify(dao).removeLibrary(argument.capture());


        SimpleListModel model = (SimpleListModel) fixture.list("lstLibrary").target().getModel();

        
        assertTrue("list does not contain deleted library", !(model.contains(library3)));
        assertTrue("list contains library that remains", model.contains(library1));
        assertTrue("list contains library that remains", model.contains(library2));
        fixture.close();

    }
    
    
    
    
}
