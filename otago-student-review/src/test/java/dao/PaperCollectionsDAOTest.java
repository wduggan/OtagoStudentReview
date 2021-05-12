/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Paper;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jesssmall
 */
public class PaperCollectionsDAOTest {

    PaperDAO papers = new PaperCollectionsDAO();
    private Paper paper1;
    private Paper paper2;
    private Paper paper3;

    @BeforeEach
    public void setUp() {
        
        //create test data
        paper1 = new Paper();
        paper1.setPaperId("1");
        paper1.setCode("COSC241");
        paper1.setTitle("Algorithms and Data Structures");
        paper1.setDescription("an understanding of the nature of algorithms and how to analyse their efficiency");
        paper1.setDepartment("Computer Science");

        paper2 = new Paper();
        paper2.setPaperId("2");
        paper2.setCode("INFO202");
        paper2.setTitle("Developing Information Systems");
        paper2.setDescription("design, build, and deploy basic information systems using modern programming languages, frameworks, and tools;");
        paper2.setDepartment("Information Science");

        paper3 = new Paper();
        paper3.setPaperId("3");
        paper3.setCode("COMP160");
        paper3.setTitle("General Programming");
        paper3.setDescription("An introduction to the art and craft of computer programming and object-oriented design using Java. \nA first look at building graphical applications.");
        paper3.setDepartment("Computer Science");

        papers.savePaper(paper1);
        papers.savePaper(paper2);

    }

    @AfterEach
    public void tearDown() {
        papers.deletePaper(paper1);
        papers.deletePaper(paper2);
        papers.deletePaper(paper3);

    }

    //checks that saving the papers adds them to the paper collection correctly
    @Test
    public void testSavePaper() {
        assertThat(papers.getPapers(), hasItem(paper1));
        assertThat(papers.getPapers(), hasItem(paper2));
        assertThat(papers.getPapers(), hasSize(2));

    }

    

    //checks that deleting the papers removes it from the collection correctly
    @Test
    public void testDeletePaper() {
        papers.deletePaper(paper1);
        assertThat(papers.getPapers(), not(hasItem(paper1)));
        assertThat(papers.getPapers(), hasSize(1));

    }

    //checks that the department from each saved paper is saved, retrieved and returned correctly
    @Test
    public void testGetDepartments() {
        assertThat(papers.getDepartments(), hasItem("Computer Science"));
        assertThat(papers.getDepartments(), hasItem("Information Science"));
    }

    
    //checks that searching by the papers ID returns the correct paper, and searching for
    // an ID that doesnt exist returns null
    @Test
    public void testSearchByID() {
        Paper paper = papers.searchByPaperID("1");
        assertThat(paper.getPaperId(), is("1"));
        assertThat(paper.getCode(), is("COSC241"));
        
        assertThat(papers.searchByPaperID("5"), is(nullValue()));

    }

    
    //checks that searching by a paper code returns the correct paper, and searching for
    // a paper code that doesnt exist returns null
    @Test
    public void testSearchByPaperCode() {
        Paper paper = papers.searchByPaperCode("INFO202");
        assertThat(paper.getCode(), is("INFO202"));
        
        assertThat(papers.searchByPaperCode("COSC250"), is(nullValue()));

    }

    
    //checks that filtering by each department returns the papers belonging to the correct department
    @Test
    public void testFilterByDepartment() {
        Collection<Paper> paper = papers.filterByDepartment("Computer Science");

        for (Paper p : paper) {
            assertThat(p.getDepartment(), is(paper1.getDepartment()));
            assertThat(p.getDepartment(), is("Computer Science"));
        }
        Collection<Paper> pap = papers.filterByDepartment("Information Science");
        for (Paper p : pap) {
            assertThat(p.getDepartment(), is(paper2.getDepartment()));
            assertThat(p.getDepartment(), is("Information Science"));

        }

    }

    //checks that filtering by the papers code returns the correct code
    @Test
    public void testFilterByPaperCode() {
        Collection<Paper> paper = papers.filterByPaperCode("COSC241");

        for (Paper p : paper) {
            assertThat(p.getDepartment(), is(paper1.getDepartment()));
            assertThat(p.getDepartment(), is("Computer Science"));
        }

    }

}
