/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import domain.Paper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caothihoangngan
 */
public class PaperJdbcDAO implements PaperDAO {
    
    private String uri = DbConnection.getDefaultConnectionUri();

    public PaperJdbcDAO() {
        
    }
    
    public PaperJdbcDAO(String uri) {
        this.uri = uri;
    }
    
    @Override
    public Collection<Paper> filterByDepartment(String department) {        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "select distinct * from paper where department = ?";
        
        try(Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) { 
            stmt.setString(1, department);
            // execute the query
            ResultSet rs = stmt.executeQuery();
            
            Collection<Paper> papers = new ArrayList<Paper>();
            
            while (rs.next()) {
                String paperId = rs.getString("paperId");
                String code = rs.getString("code");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reviews = rs.getString("reviews");
                String dep = rs.getString("department");
                
                //use the data to create a paper object
                Paper p = new Paper(paperId, code, title, description, reviews, department);
                // and put it in the collection
                papers.add(p);
            }
          
            return papers;
        }

       catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Paper> filterByPaperCode(String code) {
        String sql = "select distinct * from paper where code = ?";
        try(Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) { 
            stmt.setString(1, code);
            // execute the query
            ResultSet rs = stmt.executeQuery();
            
            Collection<Paper> papers = new ArrayList<Paper>();
            
            while (rs.next()) {
                String paperId = rs.getString("paperId");
                String cod = rs.getString("code");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reviews = rs.getString("reviews");
                String department = rs.getString("department");
                
                //use the data to create a paper object
                Paper p = new Paper(paperId, code, title, description, reviews, department);
                // and put it in the collection
                papers.add(p);
            }
          
            return papers;
        }

       catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<String> getDepartments() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "select distinct department from paper order by department";
        try ( Connection dbCon = DriverManager.getConnection(uri);
                
            PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) { 
            // execute the query

            ResultSet rs = stmt.executeQuery();
            Collection<String> departments = new HashSet<String>();

            while (rs.next()) {
                String department = rs.getString("department");
                
                departments.add(department);
            }

            return departments;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<String> getPaperCodes() {
        String sql = "select distinct code from paper order by code";
        try ( Connection dbCon = DriverManager.getConnection(uri);
                
            PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) { 
            // execute the query

            ResultSet rs = stmt.executeQuery();
            Collection<String> codes = new HashSet<String>();

            while (rs.next()) {
                String code = rs.getString("code");
                
                codes.add(code);
            }

            return codes;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Paper> getPapers() {
        String sql = "select * from paper order by paperId";

    try (
        Connection dbCon = DriverManager.getConnection(uri);  
        PreparedStatement stmt = dbCon.prepareStatement(sql)) {
        // execute the query
        ResultSet rs = stmt.executeQuery();

        // Using a List to preserve the order in which the data was returned from the query.
        Collection<Paper> papers = new ArrayList<Paper>();

        // iterate through the query results
        while (rs.next()) {

            // get the data out of the query
            String paperId = rs.getString("paperId");
            String code = rs.getString("code");
            String title = rs.getString("title");
	    String description = rs.getString("description");
	    String reviews = rs.getString("reviews");
	    String department = rs.getString("department");

            // use the data to create a student object
            Paper p = new Paper(paperId, code, title, description, reviews, department);

            // and put it in the collection
            papers.add(p);
        }

        return papers;

    } catch(SQLException ex) {
        throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletePaper(Paper paper) {
        String sql = "delete from paper where paperId =?";
        try (
        Connection dbCon = DriverManager.getConnection(uri);  
        PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) {
                    
            stmt.setString(1, paper.getPaperId());
            // execute the query
            stmt.executeUpdate();
		  
	} catch(SQLException ex) {
        throw new DAOException(ex.getMessage(), ex);
        }
	
    }

    @Override
    public void savePaper(Paper paper) {
        String sql = "insert into paper (paperId, code, title , reviews, description, department) values (?, ?, ?, ?, ?, ?)";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, paper.getPaperId());
            stmt.setString(2, paper.getCode());
            stmt.setString(3, paper.getTitle());
            stmt.setString(4, paper.getReviews());
            stmt.setString(5, paper.getDescription());
            stmt.setString(6, paper.getDepartment());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Paper searchByPaperCode(String paperCode) {
        String sql = "select * from paper where code = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
              PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(2, paperCode);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String paperId = rs.getString("paperId");
                String code = rs.getString("code");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reviews = rs.getString("reviews");
                String department = rs.getString("department");

                return new Paper(paperId, code, title, reviews, description, department);

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Paper searchByPaperID(String paperID) {
        String sql = "select * from paper where paperId = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
              PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(1, paperID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String paperId = rs.getString("paperId");
                String code = rs.getString("code");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String reviews = rs.getString("reviews");
                String department = rs.getString("department");

                return new Paper(paperId, code, title, reviews, description, department);

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
    
}
