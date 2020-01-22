/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;
import java.io.IOException;
import controller.CommunicationController;

/**
 *
 * @author jeca
 */
public class MainFormController {

    private static MainFormController instance;
    
    private CommunicationController communicationService;

    private MainFormController() {
        communicationService = CommunicationController.getInstance();
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
}
