/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.Reservation;
import domain.object.DomainObject;
import java.time.LocalTime;
import logic.systemOperation.SystemOperation;
import validator.impl.ValidatorInsertReservation;

/**
 *
 * @author jeca
 */
public class SOSaveReservation extends SystemOperation {

    public SOSaveReservation(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidatorInsertReservation();
    }

    @Override
    protected void operation() throws Exception {
        dbBroker.insert(odo);
    }
}
