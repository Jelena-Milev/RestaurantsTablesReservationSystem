/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller.panel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
            enableFields(false);
        } else if (mode == RestaurantPanelMode.UPDATE) {
            this.panel.getJbtnChange().setVisible(false);
            this.panel.getJbtnSave().setVisible(true);
            enableFields(true);
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
        try {
            boolean success = saveData();
            if (success) {
                mode = RestaurantPanelMode.VIEW;
                adjustPanel();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška pri čuvanju parametara", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void intializeForm() {
        this.mode = RestaurantPanelMode.VIEW;
        adjustPanel();
        loadData();
    }

    private void loadData() {
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
            String propertiesFileName = "config/db.properties";
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            this.panel.getJtxtURL().setText(properties.getProperty("url"));
            this.panel.getJtxtDriver().setText(properties.getProperty("driver"));
            this.panel.getJtxtUsername().setText(properties.getProperty("user"));
            this.panel.getJtxtPassword().setText(properties.getProperty("password"));
            fileInputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška pri učitavanju parametara", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableFields(boolean flag) {
        this.panel.getJtxtURL().setEnabled(flag);
        this.panel.getJtxtDriver().setEnabled(flag);
        this.panel.getJtxtUsername().setEnabled(flag);
        this.panel.getJtxtPassword().setEnabled(flag);
    }

    private boolean saveData() throws FileNotFoundException, IOException {
        try {
            checkForEmptyStrings();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška pri čuvanju parametara", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //Instantiating the properties file
        Properties props = new Properties();
        //Populating the properties file
        props.put("driver", this.panel.getJtxtDriver().getText().trim());
        props.put("url", this.panel.getJtxtURL().getText().trim());
        props.put("user", this.panel.getJtxtUsername().getText().trim());
        props.put("password", this.panel.getJtxtPassword().getText().trim());
        //Instantiating the FileInputStream for output file
        String path = "config/db.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        //Storing the properties file
        props.store(outputStrem, "This is a properties file with parameters for connecting with database");
        return true;
    }

    private void checkForEmptyStrings() throws Exception {
        if (this.panel.getJtxtDriver().getText().trim().isEmpty()) {
            throw new Exception("Morate uneti drajver");
        }
        if (this.panel.getJtxtURL().getText().trim().isEmpty()) {
            throw new Exception("Morate uneti URL");
        }
        if (this.panel.getJtxtUsername().getText().trim().isEmpty()) {
            throw new Exception("Morate uneti korisničko ime");
        }
    }
}
