/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import domain.Paper;
import domain.Tutor;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author caothihoangngan
 */
public class TutorJdbcDAO implements TutorDAO {
    
    private String uri = DbConnection.getDefaultConnectionUri();
    
    private TutorJdbcDAO(){
        
    }
    
    public TutorJdbcDAO(String uri){
        this.uri = uri;
    }
    @Override
    public Collection<Tutor> getTutors() {
        String sql = "select * from Tutor";
        try ( Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement stmt = dbCon.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            List<Tutor> tutors = new ArrayList<>();

            while (rs.next()) {
                String tutor_ID = rs.getString("tutor_ID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                ArrayList<String> paperList = (ArrayList<String>) rs.getArray("paperList");
                String description = rs.getString("description");
                String review = rs.getString("review");
                

                Tutor tutor = new Tutor(tutor_ID, name, email, paperList, description, review);
                tutors.add(tutor);
            }

            return tutors;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public ArrayList<String> getPapers() {
        String sql = "select distinct paper from tuor order by paper";
        try ( Connection dbCon = DriverManager.getConnection(uri);
                
            PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) { 
            // execute the query

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> paperLists = new ArrayList<String>();

            while (rs.next()) {
                String paper = rs.getString("papeer");
                
                paperLists.add(paper);
            }

            return paperLists;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void saveTutor(Tutor tutor) {
        String sql = "insert into Tutor (tutor_ID, name, email, paperList, description, review) values (?, ?, ?, ?)";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, tutor.getTutor_ID());
            stmt.setString(2, tutor.getName());
            stmt.setString(3, tutor.getEmail());
            stmt.setArray(4, (Array) tutor.getPaper());
            stmt.setString(5, tutor.getDescription());
            stmt.setString(6, tutor.getReview());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void removeTutor(Tutor tutor) {
        String sql = "delete from tutor where tutor_ID =?";
        try (
        Connection dbCon = DriverManager.getConnection(uri);  
        PreparedStatement stmt = dbCon.prepareStatement(sql)
        ) {
                    
            stmt.setString(1, tutor.getTutor_ID());
            // execute the query
            stmt.executeUpdate();
		  
	} catch(SQLException ex) {
        throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Tutor searchByName(String named) {
        String sql = "select * from paper where name = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
              PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(2, named);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tutor_ID = rs.getString("tutor_ID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                ArrayList<String> paperList = (ArrayList<String>) rs.getArray("paperList");
                String description = rs.getString("description");
                String review = rs.getString("review");

                return new Tutor (tutor_ID, name, email, paperList, description, review);

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Tutor searchByID(String id) {
        String sql = "select * from tutor where tutor_ID = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  
              PreparedStatement stmt = dbCon.prepareStatement(sql);) {

            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tutor_ID = rs.getString("tutor_ID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                ArrayList<String> paperList = (ArrayList<String>) rs.getArray("paperList");
                String description = rs.getString("description");
                String review = rs.getString("review");

                return new Tutor (tutor_ID, name, email, paperList, description, review);

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void removeTutorPaper(int index) {
        
    }
    
}
