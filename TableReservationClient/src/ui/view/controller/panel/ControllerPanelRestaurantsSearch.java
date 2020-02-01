/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import domain.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import ui.view.panel.JPanelRestaurantSearch;

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurantsSearch {

    private static ControllerPanelRestaurantsSearch instance;
    private List<Restaurant> allRestaurants;

    private JPanel panel;

    private ControllerPanelRestaurantsSearch() {
        allRestaurants = loadRestaurants();
        if (panel == null) {
            panel = new JPanelRestaurantSearch(true);
        }
    }

    public static ControllerPanelRestaurantsSearch getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurantsSearch();
        }
        return instance;
    }

    public List<Restaurant> loadRestaurants() {
//        return communicationService.getRestaurants();
        return null;
    }

    public List<Restaurant> getAllRestaurants() {
        return this.allRestaurants;
    }

    public List<Restaurant> filterByName(String nameTyped) {
        if (nameTyped.isEmpty() == false) {
            List<Restaurant> currentlyShowingRestaurants = allRestaurants.stream()
                    .filter(r -> r.getName().toLowerCase().startsWith(nameTyped))
                    .collect(Collectors.toList());
            return currentlyShowingRestaurants;
        }
        return allRestaurants;
    }

    public JPanel getPanel() {
        return panel;
    }
}
