/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.frame;

import controller.BLController;
import exception.CommunicationException;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.coordinator.GUICoordinator;
import ui.view.controller.panel.ControllerPanelRestaurant;
import ui.view.controller.panel.ControllerPanelRestaurantsSearch;
import ui.view.frame.JFrameAdmin;
import util.RestaurantPanelMode;
import util.SearchRestaurantsPanelMode;

/**
 *
 * @author jeca
 */
public class ControllerFrameAdmin {

    private JFrameAdmin frame;

    public void showFrame() {
        if (frame == null) {
            frame = new JFrameAdmin();
            setActionListeners();
            prepareForm();
        }
        frame.setVisible(true);
    }

    private void setActionListeners() {
        this.frame.getjMenuItemNewRestaurant().addActionListener(e -> onNewRestaurantButtonClicked());
        this.frame.getjMenuItemSearchRestaurants().addActionListener(e -> onSearchRestaurantButtonClicked());
        this.frame.getjMenuItemLogout().addActionListener(e -> onLogoutButtonClicked());
    }

    private void onNewRestaurantButtonClicked() {
        JPanel panel = ControllerPanelRestaurant.getInstance().getPanel(RestaurantPanelMode.ADD);
        setCentralPanel(panel);
    }

    private void onSearchRestaurantButtonClicked() {
        JPanel panel = ControllerPanelRestaurantsSearch.getInstance().getPanel(SearchRestaurantsPanelMode.MANAGING_RESTAURANTS);
        setCentralPanel(panel);
    }

    private void onLogoutButtonClicked() {
        int answer = JOptionPane.showConfirmDialog(this.frame, "Odajava?", "Odjavljivanje", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.NO_OPTION) {
            return;
        }
        logout();
    }

    public void logout() throws HeadlessException {
        try {
            BLController.getInstance().logout();
            this.frame.dispose();
            GUICoordinator.getInstance().logout();
        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepareForm() {
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
    }

    public void setCentralPanel(JPanel newPanel) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(newPanel, BorderLayout.CENTER);
        this.frame.revalidate();
        this.frame.repaint();
    }
}
