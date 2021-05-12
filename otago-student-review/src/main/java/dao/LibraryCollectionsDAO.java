/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Library;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author mattanderson
 */
public class LibraryCollectionsDAO implements LibraryDAO {

    private static Collection<Library> libraries = new HashSet();
    private static Map<String, Library> libraryById = new HashMap<>();
    private static Map<String, Library> libraryByName = new HashMap<>();

    @Override
    public void saveLibrary(Library library) {
        libraries.add(library);
        libraryById.put(library.getLibraryId(), library);
        libraryByName.put(library.getName(), library);
    }

    @Override
    public Collection<Library> getLibraries() {
        return libraries;
    }

    @Override
    public void removeLibrary(Library library) {
        libraries.remove(library);
    }

    @Override
     public Library searchByID(String id) {
        Library library = libraryById.get(id);
        if (library != null) {
            return library;
        } else {
            return null;
        }
     }
    
    
    @Override
    public Library searchByName(String name) {
        Library library = libraryByName.get(name);
        if (library != null) {
            return library;
        } else {
            return null;
        }
    }
}
