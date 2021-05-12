/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
import dao.PaperDAO;
import org.jooby.Jooby;

/**
 *
 * @author Finley
 */
public class PaperModule extends Jooby {
    public PaperModule(PaperDAO PaperDAO){
        get("/api/papers", () -> PaperDAO.getPapers());
        
        get("/api/papers/:id", (req) -> {
            String id = req.param("id").value();
            return PaperDAO.filterByPaperCode(id);
        });
        
        get("/api/departments", () -> PaperDAO.getDepartments());
         
        get("/api/departments/:dept", (req) -> {
            String cat = req.param("dept").value();
            return PaperDAO.filterByDepartment(cat);
        });
    }
    
}
