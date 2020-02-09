/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import domain.Reservation;
import domain.User;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;
import logic.systemOperation.SystemOperation;
import logic.systemOperation.impl.SOGetUsersReservations;

/**
 *
 * @author jeca
 */
public class ValidateDeactivateUser implements validator.Validator {

    @Override
    public void validate(Object value) throws ValidationException {
        Reservation reservation = new Reservation();
        reservation.setUser((User) value);
        List<Reservation> reservations = new LinkedList<>();
        SystemOperation so = new SOGetUsersReservations(reservation, reservations);
        try {
            so.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Reservation r : reservations) {
            if (r.isCanceled() == false) {
                throw new ValidationException("Nije moguce deaktivirati nalog za koji postoje neotkazane rezervacije");

            }
        }
    }

}
