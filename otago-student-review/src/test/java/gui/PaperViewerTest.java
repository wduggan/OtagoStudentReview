/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.PaperDAO;
import domain.Paper;
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
public class PaperViewerTest {

    private PaperDAO dao;
    private DialogFixture fixture;
    private DialogFixture fixture2;
    private Robot robot;

    private Paper paper1;
    private Paper paper2;
    private Paper paper3;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(150);

        // add some departments for testing with
        Collection<String> departments = new HashSet<>();
        departments.add("Computer Science");
        departments.add("English");

        // create a mock for the DAO
        dao = mock(PaperDAO.class);

        // stub the getDepartments method to return the test departments
        when(dao.getDepartments()).thenReturn(departments);

        Collection<Paper> papers = new HashSet<>();
        paper1 = new Paper("1", "COSC241", "Algorithms and Data Structur", "an understanding of the nature of algorithms and how to analyse their efficiency", "English");
        paper2 = new Paper("2", "INFO202", "Developing Information Systems", "design, build, and deploy basic information systems using modern programming languages, frameworks, and tools", "Information Science");
        paper3 = new Paper("3", "COMP160", "General Programming", "An introduction to the art and craft of computer programming and object-oriented design using Java. \nA first look at building graphical applications.", "Computer Science");
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        // stub the getMajors method to return the test majors
        when(dao.getDepartments()).thenReturn(departments);
        when(dao.getPapers()).thenReturn(papers);

        //stub the delete method
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                // remove the paper from the collection that getProducts() uses
                papers.remove(paper3);
                return null;
            }
        }).when(dao).deletePaper(paper3);

    }

    @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    

    @Test
    public void testEdit() {

        PaperViewer dialog = new PaperViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();
        fixture.click();

        // select item to edit
        fixture.list("papersList").selectItem(paper1.toString());

        // click the edit button
        fixture.button("btnEdit").click();

        // get the paper editor dialog that appears (which is the dialog that currently has focus)
        DialogFixture editDialog = fixture.focus();

        // verify that the UI componenents contains the papers details
        editDialog.textBox("txtID").requireText(paper1.getPaperId());
        editDialog.textBox("txtCode").requireText(paper1.getCode());
        editDialog.textBox("txtName").requireText(paper1.getTitle());
        editDialog.comboBox("cmbDepartment").requireSelection(paper1.getDepartment());
        editDialog.textBox("txtDescription").requireText(paper1.getDescription());

        // edit the name and department
        editDialog.textBox("txtName").selectAll().deleteText().enterText("Algorithms and Data Structures");
        editDialog.comboBox("cmbDepartment").selectItem("Computer Science");

        // click the save button
        editDialog.button("btnSave").click();

        // create a Mockito argument captor to use to retrieve the passed paper from the mocked DAO
        ArgumentCaptor<Paper> argument = ArgumentCaptor.forClass(Paper.class);

        // verify that the DAO.save method was called, and capture the passed paper
        verify(dao).savePaper(argument.capture());

        // retrieve the passed paper from the captor
        Paper editedPaper = argument.getValue();

        // check that the changes were saved
        assertThat("Ensure the name was changed", editedPaper.getTitle(), is("Algorithms and Data Structures"));
        assertThat("Ensure the department was changed", editedPaper.getDepartment(), is("Computer Science"));

    }
    
    
    @Test
    public void testViewPapers() {

        PaperViewer dialog = new PaperViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        verify(dao).getPapers();

        // get the model
        SimpleListModel model = (SimpleListModel) fixture.list("papersList").target().getModel();

        // check the contents
        assertTrue("list contains the expected paper", model.contains(paper1));
        assertTrue("list contains the expected paper", model.contains(paper2));
        assertTrue("list contains the expected paper", model.contains(paper3));
        assertEquals("list contains the correct number of papers", 3, model.getSize());

        fixture.close();

    }

    @Test
    public void testDeletePaper() {

        PaperViewer dialog = new PaperViewer(null, true, dao);

        // use AssertJ to control the dialog
        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // select item to edit
        fixture.list("papersList").selectItem(paper3.toString());

        // click the edit button
        fixture.button("btnDelete").click();

        DialogFixture confirm = fixture.focus();

        confirm.button(withText("Yes")).click();

        // create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
        ArgumentCaptor<Paper> argument = ArgumentCaptor.forClass(Paper.class);

        // verify that the DAO.save method was called, and capture the passed paper
        verify(dao).deletePaper(argument.capture());


        SimpleListModel model = (SimpleListModel) fixture.list("papersList").target().getModel();

        assertTrue("list does not contain deleted product", !(model.contains(paper3)));
        assertTrue("list contains product that remains", model.contains(paper1));
        assertTrue("list contains product that remains", model.contains(paper2));
        fixture.close();

    }

}
