/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.coordinator;

import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.view.controller.frame.ControllerFrameAdmin;
import ui.view.controller.frame.ControllerFrameMainMenu;
import ui.view.controller.frame.ControllerFrameUser;
import ui.view.controller.panel.ControllerPanelDiningTable;
import ui.view.controller.panel.ControllerPanelReservation;
import ui.view.controller.panel.ControllerPanelReservationsReport;
import ui.view.controller.panel.ControllerPanelRestaurant;
import ui.view.controller.panel.ControllerPanelRestaurantsSearch;
import util.ActorRole;
import util.RestaurantPanelMode;

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
        if (controllerFrameMainMenu == null) {
            controllerFrameMainMenu = new ControllerFrameMainMenu();
        }
        controllerFrameMainMenu.showFrame();
    }

    public void successfulRegistration() {
        switchToLogin();
    }

    public void showRestaurant(Restaurant restaurant) {
        JPanel panel = ControllerPanelRestaurant.getInstance().getPanel(RestaurantPanelMode.VIEW);
        ControllerPanelRestaurant.getInstance().showRestaurant(restaurant);
        addPanelToDialog(panel);
        ControllerPanelRestaurant.getInstance().addListenerForClosingDialogEvent();
    }

    public void addPanelToDialog(JPanel panel) {
        JDialog dialog = new JDialog(new JFrame(), true);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setSize(panel.getPreferredSize());
        dialog.setVisible(true);
    }

    public void refreshRestaurantSearchTable() {
        ControllerPanelRestaurantsSearch.getInstance().refreshRestaurantsTable();
    }

    public void changeTable(DiningTable table) {
        JDialog dialog = new JDialog(new JFrame(), true);
        ControllerPanelDiningTable.getInstance().setTable(table);
        ControllerPanelDiningTable.getInstance().showTable();
        JPanel panel = ControllerPanelDiningTable.getInstance().getPanel();
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
    }

    public void showActorForm(ActorRole role) {
        if (role.equals(ActorRole.ADMIN)) {
            showAdminForm();
        } else {
            showUserForm();
        }
    }

    private void showAdminForm() {
        if (controllerFrameAdmin == null) {
            controllerFrameAdmin = new ControllerFrameAdmin();
        }
        controllerFrameAdmin.showFrame();
    }

    private void showUserForm() {
        if (controllerFrameUser == null) {
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

    public void createReservation(Restaurant restaurant) {
        JPanel panel = ControllerPanelReservation.getInstance().getPanel();
        ControllerPanelReservation.getInstance().showRestaurant(restaurant);
        addPanelToDialog(panel);
    }

    public void showSavedReservations(List<Reservation> successfulReservations, List<Reservation> rejectedReservations) {
         JPanel panel = ControllerPanelReservationsReport.getInstance().getPanel();
         ControllerPanelReservationsReport.getInstance().setData(successfulReservations, rejectedReservations);
         addPanelToDialog(panel);
    }
}
