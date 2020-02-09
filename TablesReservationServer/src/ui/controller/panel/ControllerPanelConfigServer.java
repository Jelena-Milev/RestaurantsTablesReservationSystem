/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller.panel;

import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ui.view.panel.JPanelConfigServer;
import util.RestaurantPanelMode;

/**
 *
 * @author jelen
 */
public class ControllerPanelConfigServer {

    private static ControllerPanelConfigServer instance;
    private JPanelConfigServer panel;
    private RestaurantPanelMode mode;

    private ControllerPanelConfigServer() {

    }

    public static ControllerPanelConfigServer getInstance() {
        if (instance == null) {
            instance = new ControllerPanelConfigServer();
        }
        return instance;
    }

    public JPanelConfigServer getPanel() {
        this.panel = new JPanelConfigServer();
        intializeForm();
        addEventListeners();
        return panel;
    }

    private void adjustPanel() {
        if (mode == RestaurantPanelMode.VIEW) {
            this.panel.getjBtnChange().setVisible(true);
            this.panel.getjBtnSave().setVisible(false);
            this.panel.getJtxtPort().setEditable(false);
        } else if (mode == RestaurantPanelMode.UPDATE) {
            this.panel.getjBtnChange().setVisible(false);
            this.panel.getjBtnSave().setVisible(true);
            this.panel.getJtxtPort().setEditable(true);
        }
    }

    private void addEventListeners() {
        this.panel.getjBtnChange().addActionListener(e -> onChangeButtonClicked());
        this.panel.getjBtnSave().addActionListener(e -> onSaveButtonClicked());
    }

    private void onChangeButtonClicked() {
        this.mode = RestaurantPanelMode.UPDATE;
        adjustPanel();
    }

    private void onSaveButtonClicked() {
        try {
            int port = getPort();
            saveData(port);
            mode = RestaurantPanelMode.VIEW;
            adjustPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getPort() throws Exception {
        try {
            if(panel.getJtxtPort().getText().isEmpty())
                throw new Exception("Morate uneti broj porta");
            int port = Integer.parseInt(panel.getJtxtPort().getText());
            if (port < 0 || port > 65535) {
                throw new Exception("Port mora biti broj u rasponu od 0 do 65535");
            }
            return port;
        } catch (NumberFormatException ex) {
            throw new Exception("Broj porta mora biti cifra");
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
            String propertiesFileName = "config/server.properties";
            fileInputStream = new FileInputStream(propertiesFileName);
            properties.load(fileInputStream);
            this.panel.getJtxtPort().setText(properties.getProperty("socket_port"));
            fileInputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška pri učitavanju parametara", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData(int port) throws FileNotFoundException, IOException {
          //Instantiating the properties file
      Properties props = new Properties();
      //Populating the properties file
      props.put("socket_port", port+"");
      //Instantiating the FileInputStream for output file
      String path = "config/server.properties";
      FileOutputStream outputStrem = new FileOutputStream(path);
      //Storing the properties file
      props.store(outputStrem, "This is a properties file with parameters for server socket");
    }
}
