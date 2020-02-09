/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import ui.view.controller.panel.ControllerPanelConfigServer;
import ui.view.controller.panel.ControllerPanelLogin;
import ui.view.controller.panel.ControllerPanelRegister;
import ui.view.controller.panel.ControllerPanelRestaurantsSearch;
import ui.view.frame.JFrameMainMenu;
import ui.view.panel.JPanelLogin;
import ui.view.panel.JPanelRegister;
import util.SearchRestaurantsPanelMode;

/**
 *
 * @author jeca
 */
public class ControllerFrameMainMenu {
    
    private JFrameMainMenu frame;
 

    public void showFrame() {
        if(frame == null){
            frame = new JFrameMainMenu();
            setActionListeners(frame);
            prepareForm();
        }
        frame.setVisible(true);
    }

    private void setActionListeners(JFrameMainMenu frameMain) {
        frameMain.getJMenuItemUserLogin().addActionListener(e->onLoginButtonClicked());
        frameMain.getJMenuItemRegistration().addActionListener(e->onRegistrationButtonClicked());
        frameMain.getJMenuItemRestaurantSearch().addActionListener(e->onSearchRestaurantButtonClicked());
        frameMain.getjMenuItemConfigServer().addActionListener(e->onServerConfigButtonClicked());
    }

    private void onLoginButtonClicked() {
        JPanelLogin panel = ControllerPanelLogin.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onRegistrationButtonClicked() {
        JPanelRegister panel = ControllerPanelRegister.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onSearchRestaurantButtonClicked() {
        JPanel panel = ControllerPanelRestaurantsSearch.getInstance().getPanel(SearchRestaurantsPanelMode.JUST_PREVIEW);
        setCentralPanel(panel);
    }

    private void setCentralPanel(JPanel panel) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(panel, BorderLayout.CENTER);
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    private void prepareForm() {
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
    }

    public void closeForm() {
        this.frame.dispose();
    }

    public void switchToLogin() {
        JPanelLogin panel = ControllerPanelLogin.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onServerConfigButtonClicked() {
        JPanel panel = ControllerPanelConfigServer.getInstance().getPanel();
        setCentralPanel(panel);
    }
}
