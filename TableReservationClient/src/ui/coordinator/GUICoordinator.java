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
import ui.view.controller.frame.ControllerFrameAdmin;
import ui.view.controller.frame.ControllerFrameMainMenu;
import ui.view.controller.frame.ControllerFrameUser;
import ui.view.frame.JFrameMainMenu;
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
    private ControllerFrameMainMenu controllerFrameMainMenu;
    private ControllerFrameAdmin controllerFrameAdmin;
    private ControllerFrameUser controllerFrameUser;
    
    private GUICoordinator() {
    }

    public static GUICoordinator getInstance() {
        if (instance == null) {
            instance = new GUICoordinator();
        }
        return instance;
    }

    public void showMainMenu() {
        if(controllerFrameMainMenu == null){
            controllerFrameMainMenu = new ControllerFrameMainMenu();
        }
        controllerFrameMainMenu.showFrame();
    }

    public void successfulRegistration() {
        switchToLogin();
    }
    
    public void logout(JFrame form){
//        form.dispose();
//        new JFrameMain().setVisible(true);
    }

    public void showRestaurant(Restaurant restaurant) {
//        JPanel panel = new JPanelRestaurant(FormMode.VIEW);
//        ((JPanelRestaurant)panel).showRestaurant(restaurant);
//        JDialog dialog = new JDialog(new JFrame(), true);
//        dialog.add(panel);
//        dialog.pack();
//        dialog.setLocationRelativeTo(null);
//        dialog.setSize(900, 600);
//        dialog.setVisible(true);
    }

    public void changeTable(DiningTable table) {
//        JDialog dialog = new JDialog(new JFrame(), true);
//        JPanel panel = new JPanelDiningTable(table, dialog);
//        dialog.add(panel);
//        dialog.pack();
//        dialog.setLocationRelativeTo(null);
//        
//        dialog.setVisible(true);
    }

    public void showActorForm(ActorRole role) {
        if (role.equals(ActorRole.ADMIN)) {
            showAdminForm();
        } else {
            showUserForm();
        }
    }

    private void showAdminForm() {
        if(controllerFrameAdmin == null){
            controllerFrameAdmin = new ControllerFrameAdmin();
        }
        controllerFrameAdmin.showFrame();
    }

    private void showUserForm() {
        if(controllerFrameUser == null){
            controllerFrameUser = new ControllerFrameUser();
        }
        controllerFrameUser.showFrame();
    }

    public void logout() {
        this.showMainMenu();
    }

    private void switchToLogin() {
        this.controllerFrameMainMenu.switchToLogin();
    }
}
