/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Library;
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
public class LibraryCollectionsDAOTest {
    
    LibraryDAO libraries = new LibraryCollectionsDAO();
    private Library library1;
    private Library library2;
    private Library library3;
    
    
    @BeforeEach
    public void setUp() {
        
        library1 = new Library();
        library1.setLibraryId("1");
        library1.setName("Central Library");
        library1.setDescription("Main Library of Otago");
        
        
        library2 = new Library();
        library2.setLibraryId("2");
        library2.setName("Robertson Library");
        library2.setDescription("Library for Polytech and Otago Uni Students");
        
        
        library3 = new Library();
        library3.setLibraryId("3");
        library3.setName("Law Library");
        library3.setDescription("Library for Law Students");
        
        libraries.saveLibrary(library1);
        libraries.saveLibrary(library2);
       
    
    }
    
    
    
    @AfterEach
    public void tearDown() {
       libraries.removeLibrary(library1);
       libraries.removeLibrary(library2);
       libraries.removeLibrary(library3);

    }
    
    
    @Test
    public void testSaveLibrary() {
      assertThat(libraries.getLibraries(), hasItem(library1));
      assertThat(libraries.getLibraries(), hasItem(library2));
      assertThat(libraries.getLibraries(), hasSize(2));

    }
    
    
    @Test
    public void testGetLibraries(){
        assertThat(libraries.getLibraries(), hasSize(2));
    
    }
    
     @Test
    public void testDeleteLibrary(){
        libraries.removeLibrary(library1);
        assertThat(libraries.getLibraries(), not(hasItem(library1)));
        assertThat(libraries.getLibraries(), hasSize(1));

        
    }
    
    
    @Test
    public void testSearchByID(){
        Library library = libraries.searchByID("1");
        assertThat(library.getLibraryId(), is("1"));
    }
    
    
    @Test
    public void testSearchByName(){
        Library library = libraries.searchByName("Central Library");
        assertThat(library.getName(), is("Central Library"));
    
    }
    
    
    
    
    
    
    
    
}
