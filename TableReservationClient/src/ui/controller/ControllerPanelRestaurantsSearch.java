/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import util.Cuisine;
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
public class ControllerPanelRestaurantsSearch {

    private static ControllerPanelRestaurantsSearch instance;
    private CommunicationService communicationService;
    private List<Restaurant> allRestaurants;
    private List<Restaurant> currentlyShowingRestaurants;

    private ControllerPanelRestaurantsSearch() {
        try {
            communicationService = CommunicationService.getInstance();
            allRestaurants = loadRestaurants();
            currentlyShowingRestaurants = allRestaurants;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (CommunicationException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerPanelRestaurantsSearch getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurantsSearch();
        }
        return instance;
    }

    public List<Restaurant> loadRestaurants() throws CommunicationException {
        return communicationService.getRestaurants();
    }

    public List<Restaurant> getAllRestaurants() {
//        currentlyShowingRestaurants = allRestaurants;
        return this.allRestaurants;
    }

//    public List<Restaurant> findRestaurants(String nameTyped, boolean nonSmoking, boolean petsAllowed, Cuisine cuisine) {
//        List<Restaurant> filtered = this.allRestaurants.stream()
//                .filter(r -> r.isNonSmoking() == nonSmoking && r.isPetsAllowed() == petsAllowed)
//                .collect(Collectors.toList());
//
//        if (nameTyped.isEmpty() == false) {
//            filtered = filtered.stream()
//                    .filter(r -> r.getName().toLowerCase().startsWith(nameTyped))
//                    .collect(Collectors.toList());
//        }
//
//        if (cuisine != Cuisine.Sve) {
//            filtered = filtered.stream()
//                    .filter(r -> r.getCuisine().equals(cuisine.toString()))
//                    .collect(Collectors.toList());
//        }
//        return filtered;
//    }

    public List<Restaurant> filterByName(String nameTyped) {
        if (nameTyped.isEmpty() == false) {
            currentlyShowingRestaurants = currentlyShowingRestaurants.stream()
                    .filter(r -> r.getName().toLowerCase().startsWith(nameTyped))
                    .collect(Collectors.toList());
        } else {
            currentlyShowingRestaurants.removeAll(currentlyShowingRestaurants);
            currentlyShowingRestaurants.addAll(allRestaurants);
        }
        return currentlyShowingRestaurants;
    }

    public List<Restaurant> filterNonSmoking(boolean nonSmoking) {
        currentlyShowingRestaurants = currentlyShowingRestaurants.stream()
                .filter(r -> r.isNonSmoking() == nonSmoking)
                .collect(Collectors.toList());
        return currentlyShowingRestaurants;
    }
    
    public List<Restaurant> filterPetsAllowed(boolean petsAllowed) {
        currentlyShowingRestaurants = currentlyShowingRestaurants.stream()
                .filter(r -> r.isPetsAllowed()== petsAllowed)
                .collect(Collectors.toList());
        return currentlyShowingRestaurants;
    }
    
//    public List<Restaurant> filterByCuisine(Cuisine cuisine){
//        if (cuisine != Cuisine.Sve) {
//            currentlyShowingRestaurants = currentlyShowingRestaurants.stream()
//                    .filter(r -> r.getCuisine().equals(cuisine.toString()))
//                    .collect(Collectors.toList());
//        }
//        return currentlyShowingRestaurants;
//    }
}
