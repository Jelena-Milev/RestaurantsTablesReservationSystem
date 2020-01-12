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
public class ControllerLogin {

    private static ControllerLogin instance;
    private CommunicationService communicationService;

    private ControllerLogin() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerLogin getInstance() {
        if (instance == null) {
            instance = new ControllerLogin();
        }
        return instance;
    }
    
    public String login(String username, String password) throws CommunicationException {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        return communicationService.login(data);
    }
}
