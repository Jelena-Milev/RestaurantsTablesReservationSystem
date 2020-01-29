/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import exception.CommunicationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import service.CommunicationService;

/**
 *
 * @author jeca
 */
public class ControllerPanelRegister {

    private static ControllerPanelRegister instance;
    private CommunicationService communicationService;

    private ControllerPanelRegister() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static ControllerPanelRegister getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRegister();
        }
        return instance;
    }
    
    public void register(String username, String password, String name, String lastname, String mail) throws CommunicationException {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        data.put("name", name);
        data.put("lastname", lastname);
        data.put("mail", mail);
        communicationService.register(data);
    }

}
