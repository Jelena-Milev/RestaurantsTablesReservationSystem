/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import logic.systemOperation.SystemOperation;
import domain.Admin;
import domain.DiningTable;
import domain.object.DomainObject;
import domain.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class SOGetAllRestaurants extends SystemOperation {

    List<Restaurant> restaurants;

    public SOGetAllRestaurants(List<Restaurant> restaurants, DomainObject odo) {
        super();
        this.restaurants = restaurants;
        this.odo = odo;
    }

    @Override
    protected void operation() throws Exception {
        List<DomainObject> allRestaurants = dbBroker.getAll(odo);
        for (DomainObject r : allRestaurants) {
            Restaurant restaurant = (Restaurant) r;
            Admin admin = (Admin) dbBroker.get(restaurant.getAdmin()).get(0);
            restaurant.setAdmin(admin);

            DiningTable t = new DiningTable();
            t.setRestaurant(restaurant);
            List<DomainObject> tables = dbBroker.get(t);

            List<DiningTable> diningTables = tables.stream().map(DiningTable.class::cast).collect(Collectors.toList());

            diningTables = diningTables.stream().peek(dn -> dn.setRestaurant(restaurant)).collect(Collectors.toList());

            restaurant.setTables(diningTables);

            this.restaurants.add(restaurant);
        }
    }
}
