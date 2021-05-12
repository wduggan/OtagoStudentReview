/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mattanderson
 */

public class HallJdbcDAO implements HallDAO {

    private String uri = DbConnection.getDefaultConnectionUri();

    public HallJdbcDAO() {

    }

    public HallJdbcDAO(String uri) {
        this.uri = uri;
    }

    @Override
    public Collection<Hall> getHalls() {
        String sql = "select * from Hall";
        try ( Connection dbCon = DriverManager.getConnection(uri);  
            PreparedStatement prepstate = dbCon.prepareStatement(sql)) {

            ResultSet rs = prepstate.executeQuery();
            List<Hall> halls = new ArrayList<>();

            while (rs.next()) {
                String hallId = rs.getString("Hall_ID");
                String title = rs.getString("Hall_Title");
                String description = rs.getString("Description");
                String reviews = rs.getString("Review");
                
                Hall hall = new Hall(hallId, title, description);
                halls.add(hall);
            }
            return halls;
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void saveHall(Hall hall) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeHall(Hall hall) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Hall searchByID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Hall searchByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
