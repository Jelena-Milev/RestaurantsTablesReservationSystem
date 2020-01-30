/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.DiningTable;
import domain.DomainObject;
import domain.Restaurant;

/**
 *
 * @author jeca
 */
public class SOSaveRestaurant extends SystemOperation{

    public SOSaveRestaurant(DomainObject odo) {
        this.odo = odo;
        //validator
    }

    
    @Override
    protected void operation() throws Exception {
        Long id = dbBroker.insert(odo);
        Restaurant restaurant = (Restaurant)odo;
        for (DiningTable table : restaurant.getTables()) {
            table.getRestaurant().setId(id);
            dbBroker.insert(table);
        }
    }
    
}
