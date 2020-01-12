/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database.connection.ConnectionFactory;
import java.io.IOException;
import service.CommunicationService;

/**
 *
 * @author jeca
 */
public class MainFormController {

    private static MainFormController instance;
    
    private CommunicationService communicationService;

    private MainFormController() {
        communicationService = CommunicationService.getInstance();
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
