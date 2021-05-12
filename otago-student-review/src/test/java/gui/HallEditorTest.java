/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HallDAO;
import domain.Hall;
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

/**
 *
 * @author jesssmall
 */
public class HallEditorTest {

    private HallDAO dao;
    private DialogFixture fixture;
    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = BasicRobot.robotWithNewAwtHierarchy();

        robot.settings().delayBetweenEvents(40);

        // create a mock for the DAO
        dao = mock(HallDAO.class);

    }

    @AfterEach
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
    }

    @Test
    public void testSave() {
        HallsEditor dialog = new HallsEditor(null, true, dao);

        fixture = new DialogFixture(robot, dialog);
        fixture.show().requireVisible();

        // click the dialog to ensure the robot is focused in the correct window
        // (can get confused by multi-monitor and virtual desktop setups)
        fixture.click();
        
        fixture.textBox("txtHallID").enterText("1");
        fixture.textBox("txtHallName").enterText("Arana");
        fixture.textBox("txtDescription").enterText("Arana College Test.");

        
        
        // click the save button
        fixture.button("btnSave").click();

        ArgumentCaptor<Hall> argument = ArgumentCaptor.forClass(Hall.class);

        // verify that the DAO.save method was called, and capture the passed hall
        verify(dao).saveHall(argument.capture());

        // retrieve the passed paper from the captor
        Hall savedHall= argument.getValue();
        
        assertThat("Ensure the ID was saved", savedHall.getHallId(), is("1"));
        assertThat("Ensure the name was saved", savedHall.getTitle(), is("Arana"));
        assertThat("Ensure the description was saved", savedHall.getDescription(), is("Arana College Test."));
    }
    
    

}
