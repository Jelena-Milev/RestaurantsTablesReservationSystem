/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.object.DomainObject;
import logic.systemOperation.SystemOperation;

/**
 *
 * @author jeca
 */
public class SODeactivateRestaurant extends SystemOperation{

    public SODeactivateRestaurant(DomainObject odo) {
        super();
        this.odo = odo;
    }

    
    @Override
    protected void operation() throws Exception {
        dbBroker.delete(odo);
    }
    
}
