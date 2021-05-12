/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Hall;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author mattanderson
 */
public class HallCollectionsDAO implements HallDAO {

    private static Collection<Hall> halls = new HashSet();
    private static Map<String, Hall> hallsById = new HashMap<>();
    private static Map<String, Hall> hallsByName = new HashMap<>();

    @Override
    public void saveHall(Hall hall) {
        halls.add(hall);
        hallsById.put(hall.getHallId(), hall);
        hallsByName.put(hall.getTitle(), hall);
    }

    @Override
    public Collection<Hall> getHalls() {
        return halls;
    }

    @Override
    public void removeHall(Hall hall) {
        halls.remove(hall);
    }
    
    @Override
    public Hall searchByID(String id) {
        Hall hall = hallsById.get(id);
        if (hall != null) {
            return hall;
        } else {
            return null;
        }
    }
    
    @Override
     public Hall searchByName(String name) {
        Hall hall = hallsByName.get(name);
        if (hall != null) {
            return hall;
        } else {
            return null;
        }
    }

}
