/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.PaperDAO;
import domain.Paper;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
public class PaperEditorTest {

    private PaperDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(60);

        // add some departments for testing with
        Collection<String> departments = new HashSet<>();
        departments.add("Computer Science");
        departments.add("English");

        // create a mock for the DAO
        dao = mock(PaperDAO.class);

        // stub the getDepartments method to return the test departments
        when(dao.getDepartments()).thenReturn(departments);
    }

    @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    @Test
    public void testSave() {
        PaperEditor dialog = new PaperEditor(null, true, dao);

        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // click the dialog to ensure the robot is focused in the correct window
        // (can get confused by multi-monitor and virtual desktop setups)
        fixture.click();

        // enter the details of the paper
        fixture.textBox("txtID").enterText("4");
        fixture.textBox("txtCode").enterText("COSC301");
        fixture.textBox("txtName").enterText("Network Mangement and Security");
        fixture.comboBox("cmbDepartment").selectItem("Computer Science");
        fixture.textBox("txtDescription").enterText("Have sound understanding of computer systems, networks, and their security issues with hands-on experience");

        // click the save button
        fixture.button("btnSave").click();

        ArgumentCaptor<Paper> argument = ArgumentCaptor.forClass(Paper.class);

        // verify that the DAO.save method was called, and capture the passed paper
        verify(dao).savePaper(argument.capture());

        // retrieve the passed paper from the captor
        Paper savedPaper = argument.getValue();

        // check that the changes were saved
        assertThat("Ensure the ID was saved", savedPaper.getPaperId(), is("4"));
        assertThat("Ensure the code was saved", savedPaper.getCode(), is("COSC301"));
        assertThat("Ensure the description was saved", savedPaper.getTitle(), is("Network Mangement and Security"));
        assertThat("Ensure the category was saved", savedPaper.getDepartment(), is("Computer Science"));
        assertThat("Ensure the price was saved", savedPaper.getDescription(), is("Have sound understanding of computer systems, networks, and their security issues with hands-on experience"));

    }

}
