/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import ui.view.panel.JPanelConfigurationServer;
import util.RestaurantPanelMode;

/**
 *
 * @author jelen
 */
public class ControllerPanelConfigServer {
    private static ControllerPanelConfigServer instance;
    private JPanelConfigurationServer panel;
    private RestaurantPanelMode mode;

    private ControllerPanelConfigServer() {

    }

    public static ControllerPanelConfigServer getInstance() {
        if (instance == null) {
            instance = new ControllerPanelConfigServer();
        }
        return instance;
    }

    public JPanelConfigurationServer getPanel() {
        this.panel = new JPanelConfigurationServer();
        intializeForm();
        addEventListeners();
        return panel;
    }

    private void adjustPanel() {
        if(mode == RestaurantPanelMode.VIEW){
            this.panel.getjBtnChange().setVisible(true);
            this.panel.getjBtnSave().setVisible(false);
        }else if(mode == RestaurantPanelMode.UPDATE){
            this.panel.getjBtnChange().setVisible(false);
            this.panel.getjBtnSave().setVisible(true);
        }
    }

    private void addEventListeners() {
        this.panel.getjBtnChange().addActionListener(e->onChangeButtonClicked());
        this.panel.getjBtnSave().addActionListener(e->onSaveButtonClicked());
    }

    private void onChangeButtonClicked() {
        this.mode = RestaurantPanelMode.UPDATE;
        adjustPanel();
    }

    private void onSaveButtonClicked() {
        //sacuvaj iz polja u fajl
    }

    private void intializeForm() {
        this.mode = RestaurantPanelMode.VIEW;
        adjustPanel();
        loadData();
    }

    private void loadData() {
        //ucitaj podatke iz fajla
    }
}
