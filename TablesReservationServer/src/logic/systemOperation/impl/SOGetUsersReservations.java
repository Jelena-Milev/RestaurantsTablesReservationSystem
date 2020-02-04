/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.Reservation;
import domain.Restaurant;
import domain.object.DomainObject;
import java.sql.SQLException;
import java.util.List;
import logic.systemOperation.SystemOperation;

/**
 *
 * @author jeca
 */
public class SOGetUsersReservations extends SystemOperation{

    private List<Reservation> reservations;

    public SOGetUsersReservations(DomainObject odo, List<Reservation> reservations) {
        super();
        this.odo = odo;
        this.reservations = reservations;
    }

    
    @Override
    protected void operation() throws Exception {
        List<DomainObject> reservationOdos = dbBroker.getAll(odo);
        for (DomainObject reservationOdo : reservationOdos) {
            Reservation reservation = (Reservation)reservationOdo;
            
            Restaurant restaurant = getRestaurant(reservation);
            reservation.getDiningTable().setRestaurant(restaurant);
            reservation.setUser(((Reservation)odo).getUser());
            
            this.reservations.add(reservation);
        }
    }

    private Restaurant getRestaurant(Reservation r) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(r.getDiningTable().getRestaurant().getId());
        restaurant = (Restaurant) dbBroker.get(restaurant).get(0);
        return restaurant;
    }    
}
