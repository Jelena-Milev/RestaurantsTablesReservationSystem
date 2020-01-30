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

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurantNew {

    private static ControllerPanelRestaurantNew instance;
    private CommunicationService communicationService;

    private ControllerPanelRestaurantNew() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerPanelRestaurantNew getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurantNew();
        }
        return instance;
    }

    public void saveRestaurant(Restaurant restaurant) throws CommunicationException {
        this.communicationService.saveRestaurant(restaurant);
    }

}