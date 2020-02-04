/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.Reservation;
import domain.object.DomainObject;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
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
//        List<DomainObject> reservationsOdos = dbBroker.getAll(odo);
//        for (DomainObject reservationsOdo : reservationsOdos) {
//            Reservation r = (Reservation) reservationsOdo;
//            Reservation newReservation = (Reservation) odo;
//            boolean t = r.equals(newReservation);
//            if (t) {
//                dbBroker.update(odo);
//                return;
//            }
//        }
//        dbBroker.insert(odo);
        try{
            dbBroker.insert(odo);
        }catch(SQLIntegrityConstraintViolationException e){
            dbBroker.update(odo);
        }
    }
}
