/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caothihoangngan
 */
public class StudentJdbcDAO implements StudentDAO {
    private String uri = DbConnection.getDefaultConnectionUri();;
    
    public StudentJdbcDAO(){}
    
    public StudentJdbcDAO(String uri){
        this.uri = uri;
    }
    
    @Override
    public void saveStudent(Student student) {
        String sql = "insert into student (First_Name, Last_name, Email_Address, Password ) values (?,?,?,?)";
        try (
        //get connection to database
        Connection dbCon = DriverManager.getConnection(uri);  
        
        //create the statement
        PreparedStatement stmt = dbCon.prepareStatement(sql)){
        //copy the data from the student domain object into the SQL parameters
        stmt.setString (1, student.getFirst_Name());
        stmt.setString (2, student.getLast_Name());
        stmt.setString (3, student.getEmail());
        stmt.setString (4, student.getPassword());
        
        stmt.executeUpdate();  //execute the statement
    
        } catch(SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
       

    @Override
    public Student getStudent(String Student_ID) {

        String sql = "select * from student where username = ?";
        
    try (
        //get connection to database
        Connection dbCon = DriverManager.getConnection(uri);  
        
        //create the statement
        PreparedStatement stmt = dbCon.prepareStatement(sql))
        
     {
        stmt.setString(1, Student_ID);
        //execute the query
        ResultSet rs = stmt.executeQuery();
        
        // Using a List to preserve the order in which the data was returned from the query.
       

        // iterate through the query results
        if (rs.next()) {
            // get the data out of the query
            String studentId = rs.getString("Student_ID");
            String First_Name = rs.getString("First_Name");
            String Last_Name = rs.getString("Last_name");
            String Email = rs.getString("Email_Address");
            String Password = rs.getString("Password");
            
            // use the data to create a student object
            return new Student(studentId, First_Name, Last_Name, Email, Password);
            
            // and put it in the collection
            
            } else {
                
                return null;
            }
        } catch(SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
   
    }
}
