/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.xml.bind.ValidationException;
import logic.systemOperation.SystemOperation;
import logic.systemOperation.impl.SOCheckReservation;
import validator.Validator;

/**
 *
 * @author jeca
 */
public class ValidatorInsertReservation implements Validator {

    @Override
    public void validate(Object value) throws ValidationException {
        Reservation reservation = (Reservation) value;
        validateDate(reservation.getDate());
        validateTime(reservation.getTimeFrom(), reservation.getTimeTo());
        validateTableAlreadyTaken(reservation);
    }

    private void validateTableAlreadyTaken(Reservation reservation) throws ValidationException {
        SystemOperation so = new SOCheckReservation(reservation);
        try {
            so.execute();
        } catch (Exception ex) {
            throw new ValidationException(ex.getMessage());
        }
    }

    private void validateDate(Date date) throws ValidationException {
        LocalDate d = (new java.sql.Date(date.getTime())).toLocalDate();
        LocalDate now = (new java.sql.Date((new Date()).getTime())).toLocalDate();

        if (d.isBefore(now)) {
            throw new ValidationException("Datum rezervacije ne sme biti u proslosti.");
        }
    }

    private void validateTime(LocalTime timeFrom, LocalTime timeTo) throws ValidationException {
        if (timeFrom.isAfter(timeTo)) {
            throw new ValidationException("Vremenski interval je nepostojeci.");
        }
        if (timeTo.getHour() - timeFrom.getHour() < 1 || timeTo.getHour() - timeFrom.getHour() > 4) {
            throw new ValidationException("Vremenski interval ne sme biti kraci od sata niti duzi od 4 sata");
        }

    }
}
