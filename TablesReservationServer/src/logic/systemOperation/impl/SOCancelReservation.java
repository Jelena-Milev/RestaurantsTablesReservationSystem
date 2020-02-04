/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.Reservation;
import domain.object.DomainObject;
import logic.systemOperation.SystemOperation;
import validator.impl.ValidatorCancelReservation;

/**
 *
 * @author jeca
 */
public class SOCancelReservation extends SystemOperation{

    public SOCancelReservation(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidatorCancelReservation();
    }

    
    @Override
    protected void operation() throws Exception {
        ((Reservation)odo).setCanceled(true);
        this.dbBroker.update(odo);
    }    
}
