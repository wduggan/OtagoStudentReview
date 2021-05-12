/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Student;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jesssmall
 */
public class StudentCollectionsDAO implements StudentDAO{
    
     private static final Map<String, Student> students = new HashMap<>();
    
    @Override
    public void saveStudent(Student student) {
        students.put(student.getStudent_ID(), student);
    }
    
    
    @Override
    public Student getStudent(String studentID) {
        return students.get(studentID);
    }
    
}
