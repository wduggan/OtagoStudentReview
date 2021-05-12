/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Library;
import java.util.Collection;

/**
 *
 * @author mattanderson
 */
public interface LibraryDAO {
    
    Collection<Library> getLibraries();
    
    void removeLibrary(Library library);
    
    void saveLibrary(Library library);
    
    Library searchByID(String id);
    
    Library searchByName(String name);

}
