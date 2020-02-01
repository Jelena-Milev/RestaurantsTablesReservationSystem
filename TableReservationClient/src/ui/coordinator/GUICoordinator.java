/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.coordinator;

import domain.DiningTable;
import domain.Restaurant;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.view.frame.JFrameMain;
import ui.view.frame.JFrameAdmin;
import ui.view.frame.JFrameUser;
import ui.view.panel.JPanelDiningTable;
import ui.view.panel.JPanelRestaurant;
import util.ActorRole;
import util.FormMode;

/**
 *
 * @author jeca
 */
public class GUICoordinator {

    private static GUICoordinator instance;
    private JFrameMain firstForm;

    private GUICoordinator() {
    }

    public static GUICoordinator getInstance() {
        if (instance == null) {
            instance = new GUICoordinator();
        }
        return instance;
    }

//    public JFrameMain getFirstForm() {
//        return firstForm;
//    }

    public void setFirstForm(JFrameMain firstForm) {
        this.firstForm = firstForm;
    }

    public void successfulLogin(ActorRole role) {
        this.firstForm.dispose();
        if (role.equals(ActorRole.ADMIN)) {
            new JFrameAdmin().setVisible(true);
        } else {
            new JFrameUser().setVisible(true);
        }
    }

    public void successfulRegistration() {
        JOptionPane.showMessageDialog(null, "Uspesna registracija, mozete se prijaviti", "Registracija", JOptionPane.INFORMATION_MESSAGE);
        this.firstForm.switchToLogin();
    }
    
    public void logout(JFrame form){
        form.dispose();
        new JFrameMain().setVisible(true);
    }

    public void showRestaurant(Restaurant restaurant) {
        JPanel panel = new JPanelRestaurant(FormMode.VIEW);
        ((JPanelRestaurant)panel).showRestaurant(restaurant);
        JDialog dialog = new JDialog(new JFrame(), true);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setSize(900, 600);
        dialog.setVisible(true);
    }

    public void changeTable(DiningTable table) {
        JDialog dialog = new JDialog(new JFrame(), true);
        JPanel panel = new JPanelDiningTable(table, dialog);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        
        dialog.setVisible(true);
    }
}
