/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import exception.CommunicationException;
import java.io.IOException;
import service.CommunicationService;

/**
 *
 * @author jeca
 */
public class ControllerMenuAccount {

    private static ControllerMenuAccount instance;
    private CommunicationService communicationService;

    private ControllerMenuAccount() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerMenuAccount getInstance() {
        if (instance == null) {
            instance = new ControllerMenuAccount();
        }
        return instance;
    }
    
    public void logout() throws CommunicationException{
        this.communicationService.logout();
    }
}
