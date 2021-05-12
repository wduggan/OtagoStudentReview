/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.StudentCollectionsDAO;
import dao.StudentDAO;
import domain.Student;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author dugwi731
 */
public class StudentModule extends Jooby {

	StudentDAO studentDao = new StudentCollectionsDAO();

	public StudentModule(StudentDAO studentDao) {
		get("/api/students/:studentID", (req) -> {
			String studentID = req.param("studentID").value();
			
			if (studentDao.getStudent(studentID) == null) {
				return new Result().status(Status.NOT_FOUND);
			} else {
				return studentDao.getStudent(studentID);
			}
		});
		
		post("/api/register", (req, rsp) -> {
			Student student = req.body().to(Student.class);
			studentDao.saveStudent(student);
			rsp.status(Status.CREATED);
		});
	}
}
