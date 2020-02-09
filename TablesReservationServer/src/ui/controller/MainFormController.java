/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;
import java.io.IOException;
import controller.CommunicationController;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.controller.panel.ControllerPanelConfigDatabase;
import ui.controller.panel.ControllerPanelConfigServer;
import ui.view.MainForm;

/**
 *
 * @author jeca
 */
public class MainFormController {

    private static MainFormController instance;
    
    private CommunicationController communicationService;
    
    private MainForm form;

    private MainFormController() {
        this.form = new MainForm();
        communicationService = CommunicationController.getInstance();
        addActionListeners();
    }

    public static MainFormController getInstance() {
        if (instance == null) {
            instance = new MainFormController();
        }
        return instance;
    }

    public void startServer() throws IOException {
        communicationService.startServer();
    }

    public void stopServer() throws IOException {
        communicationService.stopServer();
    }

    private void addActionListeners() {
        this.form.getJmiServerStart().addActionListener(e->onStartServerClicked());
        this.form.getJmiServerStop().addActionListener(e->onStopServerClicked());
        this.form.getJmiConfigServer().addActionListener(e->onConfigServerClicked());
        this.form.getJmiConfigDatabase().addActionListener(e->onConfigDatabaseClicked());
    }

    private void onStartServerClicked() {
        try {
            this.startServer();
            this.form.getJmiServerStart().setEnabled(false);
            this.form.getJmiServerStop().setEnabled(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(form, "Server nije pokrenut", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onStopServerClicked() {
        try {
            this.stopServer();
            this.form.getJmiServerStop().setEnabled(false);
            this.form.getJmiServerStart().setEnabled(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(form, "Server nije zaustavljen", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onConfigServerClicked() {
        JPanel panel = ControllerPanelConfigServer.getInstance().getPanel();
        setCentralPanel(panel);
    }

    private void onConfigDatabaseClicked() {
        JPanel panel = ControllerPanelConfigDatabase.getInstance().getPanel();
        setCentralPanel(panel);
    }
    
    private void setCentralPanel(JPanel panel) {
        this.form.getContentPane().removeAll();
        this.form.getContentPane().add(panel, BorderLayout.CENTER);
        this.form.revalidate();
        this.form.repaint();
    }

    public void showForm() {
        if(form == null){
            form = new MainForm();
            addActionListeners();
        }
        form.setSize(600, 400);
        form.setVisible(true);
        form.setLocationRelativeTo(null);
    }
}
