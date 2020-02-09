/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;
import domain.Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.xml.bind.ValidationException;
import validator.Validator;

/**
 *
 * @author jeca
 */
public class ValidatorCreateReservation implements Validator {

    
    @Override
    public void validate(Object value) throws ValidationException {
        Reservation reservation = (Reservation)value;
        validateDate(reservation.getDate(), reservation.getTimeFrom(), reservation.getTimeTo());
        validateTime(reservation.getTimeFrom(), reservation.getTimeTo());
    }

    private void validateDate(Date date, LocalTime timeFrom, LocalTime timeTo) throws ValidationException {
        LocalDate d = (new java.sql.Date(date.getTime())).toLocalDate();
        LocalDate nowDate = (new java.sql.Date((new Date()).getTime())).toLocalDate();
        if(d.isBefore(nowDate))
            throw new ValidationException("Datum rezervacije ne sme biti u proslosti.");
        else if(d.equals(nowDate) && (timeFrom.isBefore(LocalTime.now()) || timeTo.isBefore(LocalTime.now()))){
            throw new ValidationException("Vreme rezervacije ne sme biti u proslosti.");
        }
    }

    private void validateTime(LocalTime timeFrom, LocalTime timeTo) throws ValidationException {
        if(timeFrom.isAfter(timeTo))
            throw new ValidationException("Vremenski interval je nepostojeci.");
        if(timeTo.getHour() - timeFrom.getHour() < 1 || timeTo.getHour() - timeFrom.getHour() > 4)
            throw new ValidationException("Vremenski interval ne sme biti kraci od sata niti duzi od 4 sata");
            
    }
    
}
