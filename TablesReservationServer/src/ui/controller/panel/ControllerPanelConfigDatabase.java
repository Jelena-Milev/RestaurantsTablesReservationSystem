/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller.panel;

import ui.view.panel.JPanelConfigDatabase;
import util.RestaurantPanelMode;

/**
 *
 * @author jelen
 */
public class ControllerPanelConfigDatabase {

    private static ControllerPanelConfigDatabase instance;
    private JPanelConfigDatabase panel;
    private RestaurantPanelMode mode;

    private ControllerPanelConfigDatabase() {

    }

    public static ControllerPanelConfigDatabase getInstance() {
        if (instance == null) {
            instance = new ControllerPanelConfigDatabase();
        }
        return instance;
    }

    public JPanelConfigDatabase getPanel() {
        this.panel = new JPanelConfigDatabase();
        intializeForm();
        addEventListeners();
        return panel;
    }

    private void adjustPanel() {
        if (mode == RestaurantPanelMode.VIEW) {
            this.panel.getJbtnChange().setVisible(true);
            this.panel.getJbtnSave().setVisible(false);
        } else if (mode == RestaurantPanelMode.UPDATE) {
            this.panel.getJbtnChange().setVisible(false);
            this.panel.getJbtnSave().setVisible(true);
        }
    }

    private void addEventListeners() {
        this.panel.getJbtnChange().addActionListener(e -> onChangeButtonClicked());
        this.panel.getJbtnSave().addActionListener(e -> onSaveButtonClicked());
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
