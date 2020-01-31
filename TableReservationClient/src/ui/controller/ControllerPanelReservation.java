/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import domain.DiningTable;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import service.CommunicationService;

/**
 *
 * @author jeca
 */
public class ControllerPanelReservation {

    private static ControllerPanelReservation instance;
    private CommunicationService communicationService;

    private ControllerPanelReservation() {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ControllerPanelReservation getInstance() {
        if (instance == null) {
            instance = new ControllerPanelReservation();
        }
        return instance;
    }
    
    public void createReservation(DiningTable table, Date date, LocalTime timeFrom, LocalTime timeTo) throws Exception{
        communicationService.createReservation(table, date, timeFrom, timeTo);
    }
}
