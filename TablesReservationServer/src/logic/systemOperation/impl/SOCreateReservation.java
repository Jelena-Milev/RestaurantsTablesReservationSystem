/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import logic.systemOperation.SystemOperation;
import domain.object.DomainObject;
import domain.Reservation;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.ValidationException;
import validator.impl.ValidatorCreateReservation;

/**
 *
 * @author jeca
 */
public class SOCreateReservation extends SystemOperation{

    public SOCreateReservation(DomainObject odo) {
        this.odo = odo;
        this.validator = new ValidatorCreateReservation();
    }
  
    
    @Override
    protected void operation() throws Exception {
        Reservation newR = (Reservation)odo;
        List<DomainObject> reservations = dbBroker.get(odo);
        for (DomainObject reservation : reservations) {
            Reservation r = (Reservation)reservation;
            if(r.isCanceled() == true)
                continue;
            if(newR.getTimeFrom().isAfter(r.getTimeFrom()) && newR.getTimeTo().isBefore(r.getTimeTo()))
                throw new ValidationException("Interval je zauzet");
            if(newR.getTimeFrom().isAfter(r.getTimeFrom()) && newR.getTimeFrom().isBefore(r.getTimeTo()))
                throw new ValidationException("Interval je zauzet");
            if(newR.getTimeFrom().isBefore(r.getTimeFrom()) && newR.getTimeTo().isAfter(r.getTimeTo()))
                throw new ValidationException("Interval je zauzet");
            if(newR.getTimeTo().isAfter(r.getTimeFrom()) && newR.getTimeTo().isBefore(r.getTimeTo()))
                throw new ValidationException("Interval je zauzet");
            dbBroker.insert(odo);
        }
    }
    
}
