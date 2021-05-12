/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Student;

/**
 *
 * @author jesssmall
 */
public interface StudentDAO {
    
    void saveStudent(Student student);
    
    Student getStudent(String Student_ID);
}
