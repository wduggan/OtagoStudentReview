/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Library;
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
public class LibraryJdbcDAO implements LibraryDAO {

    private String uri = DbConnection.getDefaultConnectionUri();

    public LibraryJdbcDAO() {

    }

    public LibraryJdbcDAO(String uri) {
        this.uri = uri;
    }

    @Override
    public Collection<Library> getLibraries() {

        String sql = "select * from Library";

        try ( Connection dbCon = DriverManager.getConnection(uri);  PreparedStatement prepstate = dbCon.prepareStatement(sql)) {
            ResultSet rs = prepstate.executeQuery();
            List<Library> libraries = new ArrayList<>();

            while (rs.next()) {
                String libraryId = rs.getString("Library_ID");
                String name = rs.getString("Library_Title");
                String description = rs.getString("Description");
                String reviews = rs.getString("Review");

                Library library = new Library();
                library.setLibraryId(libraryId);
                library.setName(name);
                library.setDescription(description);
                library.setReviews(reviews);
                libraries.add(library);
            }

            return libraries;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }

    }

    @Override
    public void removeLibrary(Library library) {

        String sql = "remove from Library where Library_ID = ?";

    }

    @Override
    public void saveLibrary(Library library) {

        String sql = "insert into Library (Library_ID, Library_Title, Description, Review) values (?, ?, ?, ?)";

        try ( Connection dbCon = DriverManager.getConnection(uri);  PreparedStatement prepstate = dbCon.prepareStatement(sql)) {

            prepstate.setString(1, library.getLibraryId());
            prepstate.setString(2, library.getName());
            prepstate.setString(3, library.getDescription());
            prepstate.setString(4, library.getReviews());

            prepstate.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Library searchByID(String id) {

        String sql = "select * from Library where Library_ID = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  PreparedStatement prepstate = dbCon.prepareStatement(sql)) {

            prepstate.setString(1, id);

            ResultSet rs = prepstate.executeQuery();

            if (rs.next()) {
                String libraryId = rs.getString("Library_ID");
                String name = rs.getString("Library_Title");
                String description = rs.getString("Description");
                String reviews = rs.getString("Review");

                Library library = new Library();
                library.setLibraryId(libraryId);
                library.setName(name);
                library.setDescription(description);
                library.setReviews(reviews);
                return library;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Library searchByName(String name) {

        String sql = "select * from Library where Library_Title = ?";

        try ( Connection dbCon = DriverManager.getConnection(uri);  PreparedStatement prepstate = dbCon.prepareStatement(sql);) {

            prepstate.setString(2, name);

            ResultSet rs = prepstate.executeQuery();

            if (rs.next()) {
                String libraryId = rs.getString("Library_ID");
                String libraryname = rs.getString("Library_Title");
                String description = rs.getString("Description");
                String reviews = rs.getString("Review");

                Library library = new Library();
                library.setLibraryId(libraryId);
                library.setName(libraryname);
                library.setDescription(description);
                library.setReviews(reviews);
                return library;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

}
