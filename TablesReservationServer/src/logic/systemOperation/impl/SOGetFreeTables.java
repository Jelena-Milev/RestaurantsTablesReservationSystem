/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.DiningTable;
import domain.Reservation;
import domain.object.DomainObject;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import logic.systemOperation.SystemOperation;
import validator.impl.ValidatorCreateReservation;

/**
 *
 * @author jeca
 */
public class SOGetFreeTables extends SystemOperation {

    List<DiningTable> freeTables;

    public SOGetFreeTables(Reservation reservation, List<DiningTable> freeTables) {
        super();
        this.odo = reservation;
        this.freeTables = freeTables;
        this.validator = new ValidatorCreateReservation();
    }

    @Override
    protected void operation() throws Exception {
        List<DiningTable> helpingList = new LinkedList<>();
        helpingList.addAll(((Reservation) this.odo).getDiningTable().getRestaurant().getTables());
        List<DomainObject> reservations = dbBroker.getAll(odo);
        for (DomainObject r : reservations) {
            Reservation reservation = (Reservation) r;
            if (isTerminReserved(reservation)) {
                removeTable(helpingList, reservation.getDiningTable());
            }
        }
        this.freeTables.addAll(helpingList);
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

    private void removeTable(List<DiningTable> list, DiningTable takenTable) {
        for (Iterator<DiningTable> iterator = list.iterator(); iterator.hasNext();) {
            DiningTable table = iterator.next();
            if (table.getLabel().equals(takenTable.getLabel())) {
                iterator.remove();
            }
        }
    }

}
