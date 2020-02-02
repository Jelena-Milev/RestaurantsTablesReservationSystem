/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import logic.systemOperation.SystemOperation;
import domain.DiningTable;
import domain.object.DomainObject;
import domain.Restaurant;

/**
 *
 * @author jeca
 */
public class SOSaveRestaurant extends SystemOperation {

    public SOSaveRestaurant(DomainObject odo) {
        this.odo = odo;
        //validator - ne moze insert ako vec postoji u bazi taj id
    }

    @Override
    protected void operation() throws Exception {
        Long id = dbBroker.insert(odo);
        Restaurant restaurant = (Restaurant) odo;
        restaurant.setId(id);
        for (DiningTable table : restaurant.getTables()) {
            table.setRestaurant(restaurant);
            dbBroker.insert(table);
        }
    }
}
