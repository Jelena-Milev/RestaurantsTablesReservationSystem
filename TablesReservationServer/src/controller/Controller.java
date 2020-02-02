/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Actor;
import domain.Reservation;
import domain.Restaurant;
import domain.User;
import java.util.LinkedList;
import java.util.List;
import logic.systemOperation.impl.SOCreateReservation;
import logic.systemOperation.impl.SODeactivateUser;
import logic.systemOperation.impl.SOGetAllRestaurants;
import logic.systemOperation.impl.SOLogin;
import logic.systemOperation.impl.SORegisterUser;
import logic.systemOperation.impl.SOSaveRestaurant;
import logic.systemOperation.SystemOperation;
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
        SystemOperation so = new SODeactivateUser(currentActor);
        so.execute();
    }

    public void saveRestaurant(Restaurant restaurant) throws Exception {
        SystemOperation so = new SOSaveRestaurant(restaurant);
        so.execute();
    }

    public void createReservation(Reservation reservation) throws Exception {
        SystemOperation so = new SOCreateReservation(reservation);
        so.execute();
    }
}
