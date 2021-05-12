/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Paper;
import java.util.Collection;

/**
 *
 * @author jesssmall
 */
public interface PaperDAO {
    
    
    Collection<Paper> filterByDepartment(String department);
    
    Collection<Paper> filterByPaperCode(String code);

    Collection<String> getDepartments();
    
    Collection<String> getPaperCodes();

    Collection<Paper> getPapers();

    void deletePaper(Paper paper);

    void savePaper(Paper paper);

    Paper searchByPaperCode(String paperCode);
    
    Paper searchByPaperID(String paperID);
    
    
}
