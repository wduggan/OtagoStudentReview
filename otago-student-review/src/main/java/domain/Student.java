/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package domain;
 
/**
*
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
 
/**
*
 
 
* @author caothihoangngan
*/
public class Student {
    private String Student_ID;
    private String First_Name;
    private String Last_Name;
    private String Email;
    private String Password;
   
    public Student(){}
   
    public Student(String Student_ID, String First_Name, String Last_Name, String Email, String Password){
        this.Student_ID = Student_ID;
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Email = Email;
        this.Password = Password;
       
    }
   
    public String getStudent_ID() {
        return Student_ID;
    }
 
    public void setStudent_ID(String student_ID) {
        this.Student_ID = student_ID;
    }
   
    public String getFirst_Name(){
        return First_Name;
    }
   
    public void setFirst_Name(String First_Name){
        this.First_Name = First_Name;
    }
   
    public String getLast_Name(){
        return Last_Name;
    }
   
    public void setLast_Name(String Last_Name){
        this.Last_Name = Last_Name;
    }
   
    public String getEmail(){
        return Email;
    }
   
    public void setEmail(String Email){
        this.Email = Email;
    }
   
    public String getPassword(){
        return Password;
    }
   
    public void setPassword(String Password){
        this.Password = Password;
    }
   
    @Override
    public String toString() {
            return "Student{" + "Student_ID=" + Student_ID + ", First Name=" + First_Name + ", Last Name=" + Last_Name + ", Email=" + Email + ", Password" + Password + '}';
    }
 
}