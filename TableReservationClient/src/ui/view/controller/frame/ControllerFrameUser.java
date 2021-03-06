/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.frame;

import controller.BLController;
import exception.CommunicationException;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.coordinator.GUICoordinator;
import ui.view.controller.panel.ControllerPanelAllReservation;
import ui.view.controller.panel.ControllerPanelRestaurantsSearch;
import ui.view.frame.JFrameUser;
import util.SearchRestaurantsPanelMode;

/**
 *
 * @author jeca
 */
public class ControllerFrameUser {

    private JFrameUser frame;

    public void showFrame() {
        if (frame == null) {
            frame = new JFrameUser();
            setActionListeners();
            prepareForm();
        }
        frame.setVisible(true);
    }

    private void setActionListeners() {
        this.frame.getjMenuItemReservationCreate().addActionListener(e -> onCreateReservationButtonClicked());
        this.frame.getjMenuItemAllReservations().addActionListener(e -> onAllReservationsButtonClicked());
        this.frame.getjMenuItemAccountDeactivate().addActionListener(e -> onAccountDeactivateButtonClicked());
        this.frame.getjMenuItemAccountLogout().addActionListener(e -> onAccountLogoutButtonClicked());
    }

    private void onCreateReservationButtonClicked() {
        JPanel panel = ControllerPanelRestaurantsSearch.getInstance().getPanel(SearchRestaurantsPanelMode.CREATING_RESERVATIONS);
        setCentralPanel(panel);
    }

    private void onAllReservationsButtonClicked() {
        JPanel panel = ControllerPanelAllReservation.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onAccountDeactivateButtonClicked() {
        int answer = JOptionPane.showConfirmDialog(frame, "Da li ste sigurni da zelite da deaktivirate svoj nalog?", "Deaktivacija naloga", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            deactivateAccount();
        }
    }

    private void onAccountLogoutButtonClicked() {
        int answer = JOptionPane.showConfirmDialog(frame, "Da li ste sigurni da zelite da se odjavite?", "Odjavljivanje", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            logout();
        }
    }

    public void deactivateAccount() {
        try {
            BLController.getInstance().deactivateAccount();
            this.frame.dispose();
            GUICoordinator.getInstance().logout();
        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void logout() {
        try {
            BLController.getInstance().logout();
            this.frame.dispose();
            GUICoordinator.getInstance().logout();
        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepareForm() {
//        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }

    public void setCentralPanel(JPanel newPanel) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(newPanel, BorderLayout.CENTER);
        this.frame.revalidate();
        this.frame.repaint();
    }

}
