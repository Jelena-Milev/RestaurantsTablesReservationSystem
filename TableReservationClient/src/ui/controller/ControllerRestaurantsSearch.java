/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import domain.Cuisine;
import domain.Restaurant;
import exception.CommunicationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import service.CommunicationService;
import sun.print.resources.serviceui;

/**
 *
 * @author jeca
 */
public class ControllerRestaurantsSearch {
    
    private static ControllerRestaurantsSearch instance;
    private CommunicationService communicationService;
    private List<Restaurant> restaurants;
    

    private ControllerRestaurantsSearch() {
        try {
            communicationService = CommunicationService.getInstance();
            restaurants = loadRestaurants();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CommunicationException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerRestaurantsSearch getInstance() {
        if (instance == null) {
            instance = new ControllerRestaurantsSearch();
        }
        return instance;
    }
    
    public List<Restaurant> loadRestaurants() throws CommunicationException{
        return communicationService.getRestaurants();
    }
     
    public List<Restaurant> getAllRestaurants(){
        return this.restaurants;
    }
    
    public List<Restaurant> findRestaurants(String nameTyped, boolean nonSmoking, boolean petsAllowed, Cuisine cuisine) {

//        List<Restaurant> allRestaurants = new LinkedList<>();
//        allRestaurants.addAll(this.restaurants);
//        List<Restaurant> sorted = new LinkedList<>();

        List<Restaurant> filtered = this.restaurants.stream()
                .filter(r -> r.isNonSmoking() == nonSmoking && r.isPetsAllowed() == petsAllowed)
                .collect(Collectors.toList());
        
        if(nameTyped.isEmpty() == false){
            filtered = filtered.stream()
                    .filter(r->r.getName().toLowerCase().startsWith(nameTyped))
                    .collect(Collectors.toList());
        }
        
        if(cuisine!=Cuisine.Sve){
            filtered = filtered.stream()
                    .filter(r->r.getCuisine().equals(cuisine.toString()))
                    .collect(Collectors.toList());
        }
        
//        if (nameTyped.isEmpty() == false) {
//            for (Restaurant r : allRestaurants) {
//                if (r.getName().toLowerCase().startsWith(nameTyped.toLowerCase())) {
//                    sorted.add(r);
//                }
//            }
//            for (Restaurant r : sorted) {
//                if ((r.isNonSmoking() == nonSmoking && r.isPetsAllowed() == petsAllowed) == false) {
//                    sorted.remove(r);
//                }
//            }
//
//            if (cuisine.equals("Sve") == false) {
//                for (Restaurant r : sorted) {
//                    if (r.getCuisine().equals(cuisine) == false) {
//                        sorted.remove(r);
//                    }
//                }
//            }
//        }
        return filtered;
    }
}
