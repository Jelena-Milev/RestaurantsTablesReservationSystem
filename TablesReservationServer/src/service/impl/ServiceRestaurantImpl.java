/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import database.broker.DatabaseBroker;
import domain.DomainObject;
import domain.Restaurant;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ServiceRestaurant;

/**
 *
 * @author jeca
 */
public class ServiceRestaurantImpl implements ServiceRestaurant {

    private DatabaseBroker dbBroker;

    public ServiceRestaurantImpl() {
        dbBroker = DatabaseBroker.getInstance();
    }

    @Override
    public List<Restaurant> getAll() throws SQLException, Exception {
        List<Restaurant> restaurants = new LinkedList<>();
       
        List<DomainObject> restaurantsAbstract = dbBroker.getAll(new Restaurant());
        
        if (restaurantsAbstract == null) {
            throw new Exception("Greska pri ucitavanju restorana");
        }
        restaurantsAbstract.forEach((restaurant) -> {
            restaurants.add((Restaurant) restaurant);
        });

        return restaurants;
    }
}
