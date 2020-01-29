/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.DomainObject;
import domain.Restaurant;
import java.util.List;

/**
 *
 * @author jeca
 */
public class SOGetAllRestaurants extends SystemOperation{

    List<Restaurant> restaurants;
    
    public SOGetAllRestaurants(List<Restaurant> restaurants, DomainObject odo) {
        this.restaurants = restaurants;
        this.odo = odo;
    }

    @Override
    protected void operation() throws Exception {
        List<DomainObject> odos = dbBroker.getAll(odo);
        for (DomainObject odo1 : odos) {
            this.restaurants.add((Restaurant)odo1);
        }
    }
    
//    public List<Restaurant> getAllRestaurants(){
//        
//    }
    
}
