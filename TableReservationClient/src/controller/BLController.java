/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import exception.CommunicationException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    private BLController() throws CommunicationException {
        try {
            communicationService = CommunicationService.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Server je ugasen");
        }
    }

    public static BLController getInstance() throws CommunicationException {
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

    public List<Restaurant> getAllRestaurants() throws CommunicationException {
        return communicationService.getAllRestaurants();
    }

    public void saveRestaurant(Restaurant restaurant) throws CommunicationException {
        communicationService.saveRestaurant(restaurant);
    }

    public void updateRestaurant(Restaurant restaurant) throws CommunicationException {
        communicationService.updateRestaurant(restaurant);
    }

    public List<DiningTable> findFreeTables(Restaurant restaurant, Date date, LocalTime timeFrom, LocalTime timeTo) throws CommunicationException {
        Map<String, Object> data = new HashMap<>();
        data.put("restaurant", restaurant);
        data.put("date", date);
        data.put("timeFrom", timeFrom);
        data.put("timeTo", timeTo);
        return communicationService.findFreeTables(data);
    }

    public Map<String, Object> saveReservations(List<Reservation> reservations) {
        List<Reservation> successfulReservations = new LinkedList<>();
        List<Reservation> rejectedReservations = new LinkedList<>();
        
        for (Reservation reservation : reservations) {
            try {
                communicationService.saveReservation(reservation);
                successfulReservations.add(reservation);
            } catch (CommunicationException ex) {
                rejectedReservations.add(reservation);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("successfulReservations", successfulReservations);
        map.put("rejectedReservations", rejectedReservations);
        return map;
    }

    public List<Reservation> getAllReservations() throws CommunicationException {
        return communicationService.getAllReservations();
    }

    public void cancelReservation(Reservation reservation) throws CommunicationException {
        communicationService.cancelReservation(reservation);
    }

    public void deactivateRestaurant(Restaurant restaurant) throws CommunicationException{
        this.communicationService.deactivateRestaurant(restaurant);
    }
}
