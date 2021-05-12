/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jesssmall
 */
public class HallCollectionsDAOTest {
    
    HallDAO halls = new HallCollectionsDAO();
    private Hall hall1;
    private Hall hall2;
    private Hall hall3;
    
    
    
    @BeforeEach
    public void setUp() {
        hall1 = new Hall();
        hall1.setHallId("1");
        hall1.setTitle("Arana College");
        hall1.setDescription("Arana College Description Test");
        
        hall2 = new Hall();
        hall2.setHallId("2");
        hall2.setTitle("Unicol");
        hall2.setDescription("Unicol Description Test");
        
        
        hall3 = new Hall();
        hall3.setHallId("3");
        hall3.setTitle("Selwyn College");
        hall3.setDescription("Selwyn Description Test");
        
        halls.saveHall(hall1);
        halls.saveHall(hall2);
        
    }
    
    @AfterEach
    public void tearDown() {
       halls.removeHall(hall1);
       halls.removeHall(hall2);
       halls.removeHall(hall3);

    }
    
    
    
    @Test
    public void testSaveHall() {
      assertThat(halls.getHalls(), hasItem(hall1));
      assertThat(halls.getHalls(), hasItem(hall2));
      assertThat(halls.getHalls(), hasSize(2));

    }
    
    @Test
    public void testGetHalls(){
        assertThat(halls.getHalls(), hasSize(2));
    
    }
    
    
    @Test
    public void testDeleteHall(){
        halls.removeHall(hall1);
        assertThat(halls.getHalls(), not(hasItem(hall1)));
        assertThat(halls.getHalls(), hasSize(1));

        
    }
    
    @Test
    public void testSearchByID(){
        Hall hall = halls.searchByID("1");
        assertThat(hall.getHallId(), is("1"));
    }
    
    
    @Test
    public void testSearchByName(){
        Hall hall = halls.searchByName("Arana College");
        assertThat(hall.getTitle(), is("Arana College"));
    
    }
    
    
    
    
}
