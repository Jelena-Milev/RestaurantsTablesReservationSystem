/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import domain.Reservation;
import java.time.LocalDate;
import java.util.Date;
import javax.xml.bind.ValidationException;
import validator.Validator;

/**
 *
 * @author jeca
 */
public class ValidatorCancelReservation implements Validator {

    @Override
    public void validate(Object value) throws ValidationException {
        Reservation reservation = (Reservation) value;
        validateStatus(reservation.isCanceled());
        validateDate(reservation.getDate());
    }

    private void validateDate(Date date) throws ValidationException {
        LocalDate d = (new java.sql.Date(date.getTime())).toLocalDate();
        LocalDate now = (new java.sql.Date((new Date()).getTime())).toLocalDate();

        if (d.isBefore(now)) {
            throw new ValidationException("Nije moguce otkazati prosle rezervacije.");
        }
    }

    private void validateStatus(boolean canceled) throws ValidationException {
        if (canceled) {
            throw new ValidationException("Rezervacija je vec otkazana");
        }
    }

}
