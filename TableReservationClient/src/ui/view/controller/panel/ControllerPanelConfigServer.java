/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;
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
        if (mode == RestaurantPanelMode.VIEW) {
            this.panel.getjBtnChange().setVisible(true);
            this.panel.getjBtnSave().setVisible(false);
            enableFields(false);
        } else if (mode == RestaurantPanelMode.UPDATE) {
            this.panel.getjBtnChange().setVisible(false);
            this.panel.getjBtnSave().setVisible(true);
            enableFields(true);
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
        String adress = panel.getJtxtAdress().getText();
        if(adress.isEmpty()){
            JOptionPane.showMessageDialog(panel, "Morate uneti adresu servera", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int port = getPort();
            saveData(port, adress);
            mode = RestaurantPanelMode.VIEW;
            adjustPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
            this.panel.getJtxtPort().setText(properties.getProperty("port"));
            this.panel.getJtxtAdress().setText(properties.getProperty("adress"));
            fileInputStream.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), "Greška pri učitavanju parametara", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableFields(boolean flag) {
        this.panel.getJtxtPort().setEditable(flag);
        this.panel.getJtxtAdress().setEditable(flag);
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

    private void saveData(int port, String adress) throws IOException {
            //Instantiating the properties file
      Properties props = new Properties();
      //Populating the properties file
      props.put("port", port+"");
      props.put("adress", adress);
      //Instantiating the FileInputStream for output file
      String path = "config/server.properties";
      FileOutputStream outputStrem = new FileOutputStream(path);
      //Storing the properties file
      props.store(outputStrem, "This is a properties file with parameters for server socket");
    }
}
