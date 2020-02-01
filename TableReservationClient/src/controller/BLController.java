/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
public class BLController {

    private static BLController instance;
    private CommunicationService communicationService;

    private BLController() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static BLController getInstance() {
        if (instance == null) {
            instance = new BLController();
        }
        return instance;
    }

    public ActorRole login(String username, String password) throws CommunicationException {
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        return communicationService.login(data);
    }

    public void logout() throws CommunicationException {
        communicationService.logout();
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

    public void deactivateAccount() throws CommunicationException {
        this.communicationService.deactivateAccount();

    }
}
