/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.Reservation;
import domain.object.DomainObject;
import exception.ValidationException;
import java.time.LocalTime;
import java.util.List;
import logic.systemOperation.SystemOperation;

/**
 *
 * @author jeca
 */
public class SOCheckReservation extends SystemOperation{

    public SOCheckReservation(DomainObject odo){
        super();
        this.odo = odo;
    }

    
    @Override
    protected void operation() throws Exception {
        List<DomainObject> reservations = dbBroker.get(odo);
        
        for (DomainObject r : reservations) {
            Reservation reservation = (Reservation) r;
            if (isTerminReserved(reservation)) {
                throw new ValidationException("Sto je zauzet u tom terminu");
            }
        }
    }
    
    private boolean isTerminReserved(Reservation r) {
        LocalTime timeFrom = ((Reservation) odo).getTimeFrom();
        LocalTime timeTo = ((Reservation) odo).getTimeTo();

        if ((timeFrom.isAfter(r.getTimeFrom()) || timeFrom.equals(r.getTimeFrom()))
                && (timeTo.isBefore(r.getTimeTo()) || timeTo.equals(r.getTimeTo()))) {
            return true;
        }
        
        if ((timeFrom.isAfter(r.getTimeFrom()) && timeFrom.isBefore(r.getTimeTo()))
                || (timeFrom.equals(r.getTimeFrom()) && timeFrom.isBefore(r.getTimeTo()))) {
            return true;
        }

        if ((timeFrom.isBefore(r.getTimeFrom()) || timeFrom.equals(r.getTimeFrom()))
                && (timeTo.isAfter(r.getTimeTo()) || timeTo.equals(r.getTimeTo()))) {
            return true;
        }

        if ((timeTo.isAfter(r.getTimeFrom()) && timeTo.isBefore(r.getTimeTo()))
                || (timeTo.equals(r.getTimeTo()) && timeTo.isAfter(r.getTimeFrom()))) {
            return true;
        }

        return false;
    }

}
