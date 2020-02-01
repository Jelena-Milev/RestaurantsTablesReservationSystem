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
import util.ActorRole;

/**
 *
 * @author jeca
 */
public class ControllerPanelLogin {

    private static ControllerPanelLogin instance;
    private CommunicationService communicationService;

    private ControllerPanelLogin() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerPanelLogin getInstance() {
        if (instance == null) {
            instance = new ControllerPanelLogin();
        }
        return instance;
    }
    
    public ActorRole login(String username, String password) throws CommunicationException {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        return communicationService.login(data);
    }
}
