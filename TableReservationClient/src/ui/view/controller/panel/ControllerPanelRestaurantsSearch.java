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
            JOptionPane.showMessageDialog(panel, "Došlo je do greške pri učitavanju restorana", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public JPanel getPanel(SearchRestaurantsPanelMode mode) {
        initializePanel(mode);
        return panel;
    }

    private void initializePanel(SearchRestaurantsPanelMode mode) {
        panel = new JPanelRestaurantSearch();
        addEventHandlers();
        preparePanel();
        adjustPanel(mode);
    }

    private void addEventHandlers() {
        this.panel.getJtxtName().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String nameTyped = textField.getText();
                showRestaurants(filterByName(nameTyped));
            }
        });
        this.panel.getJbtnShowRestaurant().addActionListener(e -> onShowRestaurantButtonClicked());
        this.panel.getJbtnCreateReservation().addActionListener(e -> onCreateReservationButtonClicked());
    }

    private void onShowRestaurantButtonClicked() {
        try {
            Restaurant restaurant = getSelectedRestaurant();
            GUICoordinator.getInstance().showRestaurant(restaurant);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCreateReservationButtonClicked() {
        try {
            Restaurant restaurant = getSelectedRestaurant();
            GUICoordinator.getInstance().createReservation(restaurant);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
//        System.out.println("Create reservation clicked");
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

    public void refreshRestaurantsTable() {
        this.allRestaurants = loadRestaurants();
        TableModelRestaurants modelRestaurants = (TableModelRestaurants) panel.getJtableRestaurants().getModel();
        modelRestaurants.setRestaurants(allRestaurants);
        modelRestaurants.fireTableDataChanged();
    }
}
