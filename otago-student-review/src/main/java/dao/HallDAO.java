/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import java.util.Collection;

/**
 *
 * @author mattanderson
 */
public interface HallDAO {
    
    void saveHall(Hall hall);
    
    Collection<Hall> getHalls();
    
    void removeHall(Hall hall);
    
    Hall searchByID(String id);
    
    Hall searchByName(String name);
    
}
