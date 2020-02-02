/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import domain.Restaurant;
import exception.CommunicationException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ui.coordinator.GUICoordinator;
import ui.view.components.table.TableModelRestaurants;
import ui.view.panel.JPanelRestaurantSearch;
import util.SearchRestaurantsPanelMode;

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurantsSearch {

    private static ControllerPanelRestaurantsSearch instance;

    private List<Restaurant> allRestaurants;

    private JPanelRestaurantSearch panel;

    private ControllerPanelRestaurantsSearch() {
        allRestaurants = loadRestaurants();

        initializePanel();
        addEventHandlers();
        preparePanel();
    }

    public static ControllerPanelRestaurantsSearch getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurantsSearch();
        }
        return instance;
    }

    private List<Restaurant> loadRestaurants() {
        //make it prettier
        try {
            return BLController.getInstance().getAllRestaurants();
        } catch (CommunicationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public JPanel getPanel(SearchRestaurantsPanelMode mode) {
        adjustPanel(mode);
        return panel;
    }

    private void initializePanel() {
        if (panel == null) {
            panel = new JPanelRestaurantSearch();
        }
    }
    
    private void addEventHandlers() {
        this.panel.getJtxtName().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String nameTyped = textField.getText();
                showRestaurants(filterByName(nameTyped));
            }
        });
        this.panel.getJbtnShowRestaurant().addActionListener(e -> onShowRestaurantButtonTyped());
        this.panel.getJbtnCreateReservation().addActionListener(e -> onCreateReservationButtonTyped());
    }

    private void onShowRestaurantButtonTyped() {
        try {
            Restaurant restaurant = getSelectedRestaurant();
            GUICoordinator.getInstance().showRestaurant(restaurant);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCreateReservationButtonTyped() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showRestaurants(List<Restaurant> restaurants) {
        TableModelRestaurants model = (TableModelRestaurants) panel.getJtableRestaurants().getModel();
        model.setRestaurants(restaurants);
    }

    private Restaurant getSelectedRestaurant() throws Exception {
        int rowSelected = this.panel.getJtableRestaurants().getSelectedRow();
        if (rowSelected == -1) {
            throw new Exception("Morate izabrati restoran.");
        }
        TableModelRestaurants model = (TableModelRestaurants) this.panel.getJtableRestaurants().getModel();
        return model.getRestaurant(rowSelected);
    }

    private List<Restaurant> filterByName(String nameTyped) {
        if (nameTyped.isEmpty()) {
            return allRestaurants;
        }
        List<Restaurant> currentlyShowingRestaurants = allRestaurants.stream()
                .filter(r -> r.getName().toLowerCase().startsWith(nameTyped))
                .collect(Collectors.toList());
        return currentlyShowingRestaurants;
    }

    private void preparePanel() {
        fillRestaurantsTable();
    }

    private void fillRestaurantsTable() {
        TableModelRestaurants model = new TableModelRestaurants(this.allRestaurants);
        this.panel.getJtableRestaurants().setModel(model);
    }

    private void adjustPanel(SearchRestaurantsPanelMode mode) {
        switch (mode) {
            case JUST_PREVIEW:
                panel.getJbtnCreateReservation().setVisible(false);
                panel.getJbtnShowRestaurant().setVisible(false);
                break;
            case MANAGING_RESTAURANTS:
                panel.getJbtnCreateReservation().setVisible(false);
                panel.getJbtnShowRestaurant().setVisible(true);
                break;
            case CREATING_RESERVATIONS:
                panel.getJbtnCreateReservation().setVisible(true);
                panel.getJbtnShowRestaurant().setVisible(false);
                break;
        }
    }
}
