/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import domain.Restaurant;
import exception.CommunicationException;
import java.io.IOException;
import java.util.List;
import javax.swing.JPanel;
import service.CommunicationService;
import ui.view.panel.JPanelRestaurant;
import util.FormMode;

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurantNew {

    private static ControllerPanelRestaurantNew instance;
    private CommunicationService communicationService;

    private JPanel panel;

    private ControllerPanelRestaurantNew() {
        if (panel == null) {
            panel = new JPanelRestaurant(FormMode.ADD);
        }
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

    public JPanel getPanel() {
        return panel;
    }

}
