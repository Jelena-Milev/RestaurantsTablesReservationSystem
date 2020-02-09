/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Actor;
import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import domain.User;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import logic.systemOperation.impl.SODeactivateUser;
import logic.systemOperation.impl.SOGetAllRestaurants;
import logic.systemOperation.impl.SOLogin;
import logic.systemOperation.impl.SORegisterUser;
import logic.systemOperation.impl.SOSaveRestaurant;
import logic.systemOperation.SystemOperation;
import logic.systemOperation.impl.SOCancelReservation;
import logic.systemOperation.impl.SOGetFreeTables;
import logic.systemOperation.impl.SOGetUsersReservations;
import logic.systemOperation.impl.SOSaveReservation;
import logic.systemOperation.impl.SOUpdateRestaurant;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class Controller {

    private static Controller instance;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Actor login(String username, String password) throws Exception {
        Actor actor = new Actor(username, password);
        SystemOperation so;
        so = new SOLogin(actor);
        so.execute();
        return (Actor) so.getDomainObject();
    }

    public void register(User user) throws Exception {
        SystemOperation so = new SORegisterUser(user);
        so.execute();
    }

    public List<Restaurant> getAllRestaurants() throws Exception {
        List<Restaurant> restaurants = new LinkedList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setStatus(DomainObjectStatus.ACTIVE);

        SystemOperation so = new SOGetAllRestaurants(restaurants, restaurant);
        so.execute();
        return restaurants;
    }

    public void deactivateUser(Actor currentActor) throws Exception {
        SystemOperation so = new SODeactivateUser((User)currentActor);
        so.execute();
    }

    public void saveRestaurant(Restaurant restaurant) throws Exception {
        SystemOperation so = new SOSaveRestaurant(restaurant);
        so.execute();
    }

    public void saveReservation(Reservation reservation) throws Exception {
        SystemOperation so = new SOSaveReservation(reservation);
        so.execute();
    }

    public void updateRestaurant(Restaurant restaurant) throws Exception {
        SystemOperation so = new SOUpdateRestaurant(restaurant);
        so.execute();
    }

    public List<DiningTable> getFreeTables(Map map) throws Exception {
        Restaurant restaurant = (Restaurant) map.get("restaurant");
        Date date = (Date) map.get("date");
        LocalTime timeFrom = (LocalTime) map.get("timeFrom");
        LocalTime timeTo = (LocalTime) map.get("timeTo");

        Reservation reservation = new Reservation(restaurant, date, timeFrom, timeTo);
        List<DiningTable> diningTables = new LinkedList<>();
        SystemOperation so = new SOGetFreeTables(reservation, diningTables);
        so.execute();
        return diningTables;
    }

    public List<Reservation> getUsersReservations(User user) throws Exception {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        List<Reservation> reservations = new LinkedList<>();
        SystemOperation so = new SOGetUsersReservations(reservation, reservations);
        so.execute();
        return reservations;
    }

    public void cancelReservation(Reservation reservation) throws Exception {
        SystemOperation so = new SOCancelReservation(reservation);
        so.execute();
    }
}
