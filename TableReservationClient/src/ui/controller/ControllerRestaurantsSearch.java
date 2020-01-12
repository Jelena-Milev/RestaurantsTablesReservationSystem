/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import domain.Restaurant;
import exception.CommunicationException;
import java.io.IOException;
import java.util.List;
import service.CommunicationService;
import sun.print.resources.serviceui;

/**
 *
 * @author jeca
 */
public class ControllerRestaurantsSearch {
    
    private static ControllerRestaurantsSearch instance;
    private CommunicationService communicationService;
    

    private ControllerRestaurantsSearch() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerRestaurantsSearch getInstance() {
        if (instance == null) {
            instance = new ControllerRestaurantsSearch();
        }
        return instance;
    }
    
    public List<Restaurant> getRestaurants() throws CommunicationException{
        return communicationService.getRestaurants();
    }
}
