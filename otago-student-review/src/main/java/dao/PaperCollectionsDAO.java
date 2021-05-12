/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Paper;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author jesssmall
 */
public class PaperCollectionsDAO implements PaperDAO {

    private static final Collection<Paper> papers = new HashSet<>();
    private static final Collection<String> department = new HashSet<>();
    private static final Collection<String> searchCode = new HashSet<>();
    private static final Map<String, Paper> paperCode = new HashMap<>();
    private static final Map<String, Paper> paperID = new HashMap<>();
    private static final Multimap<String, Paper> departments = HashMultimap.create();
    private static final Multimap<String, Paper> codes = HashMultimap.create();

    @Override
    public void savePaper(Paper paper) {
        //adds paper to paper collection
        papers.add(paper);

        //adds department to department collection
        department.add(paper.getDepartment());

        searchCode.add(paper.getCode());

        //adds paper by paper id
        paperID.put(paper.getPaperId(), paper);

        //adds paper by paper code
        paperCode.put(paper.getCode(), paper);

        //adds paper to department collection by department
        departments.put(paper.getDepartment(), paper);

        //adds paper codes to papercode collection by papercode
        codes.put(paper.getCode(), paper);

    }

    @Override
    //returns all papers in the paper collection
    public Collection<Paper> getPapers() {
        return papers;
    }

    @Override
    public void deletePaper(Paper paper) {
        //deletes paper from paper collection
        papers.remove(paper);

        //removes paper from department collection
        department.remove(paper.getDepartment());
    }

    @Override
    //returns all departments
    public Collection<String> getDepartments() {
        return department;
    }

    @Override
    //returns all departments
    public Collection<String> getPaperCodes() {
        return searchCode;
    }

    @Override
    //returns paper by paper code when searched, returns null if papercode does not exist
    public Paper searchByPaperCode(String code) {
        Paper paper = paperCode.get(code);
        if (paper != null) {
            return paper;
        } else {
            return null;
        }
    }

    @Override
    //returns paper by paper ID when searched, returns null if paperID does not exist
    public Paper searchByPaperID(String id) {
        Paper paper = paperID.get(id);
        if (paper != null) {
            return paper;
        } else {
            return null;
        }
    }

    @Override
    //displays papers only by which departments are needed
    public Collection<Paper> filterByDepartment(String department) {
        Collection<Paper> paperDepartment = departments.get(department);
        return paperDepartment;
    }

    @Override
    //displays papers only by which papercodes are needed
    public Collection<Paper> filterByPaperCode(String code) {
        Collection<Paper> paperCode = codes.get(code);
        return paperCode;
    }

}
