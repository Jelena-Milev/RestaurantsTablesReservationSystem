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
import ui.view.controller.ControllerMenuAccount;
import ui.view.controller.panel.ControllerPanelReservation;
import ui.view.controller.panel.ControllerPanelRestaurantsSearch;
import ui.view.frame.JFrameAdmin;
import ui.view.frame.JFrameUser;

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
        this.frame.getjMenuItemReservationCancel().addActionListener(e -> onCancelReservationButtonClicked());
        this.frame.getjMenuItemAccountDeactivate().addActionListener(e -> onAccountDeactivateButtonClicked());
        this.frame.getjMenuItemAccountLogout().addActionListener(e -> onAccountLogoutButtonClicked());
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

    private void onCreateReservationButtonClicked() {
        JPanel panel = ControllerPanelRestaurantsSearch.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onCancelReservationButtonClicked() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void onAccountDeactivateButtonClicked() {
        int answer = JOptionPane.showConfirmDialog(frame, "Da li ste sigurni da zelite da deaktivirate svoj nalog?", "Deaktivacija naloga", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            deactivateAccount();
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

    private void onAccountLogoutButtonClicked() {
        int answer = JOptionPane.showConfirmDialog(frame, "Da li ste sigurni da zelite da se odjavite?", "Odjavljivanje", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            logout();
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

}
