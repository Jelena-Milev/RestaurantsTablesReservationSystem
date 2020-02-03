/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import domain.DiningTable;
import domain.Restaurant;
import domain.object.DomainObject;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import logic.systemOperation.SystemOperation;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class SOUpdateRestaurant extends SystemOperation {

    public SOUpdateRestaurant(DomainObject odo) {
        super();
        this.odo = odo;
        //validator - ne moze update ako restoran vec ne postoji u bazi
    }

    @Override
    protected void operation() throws Exception {
        dbBroker.update(odo);

        Restaurant restaurant = (Restaurant) odo;
        List<DiningTable> diningTables = getRestaurantTables(restaurant);

        for (DiningTable dtForSaving : restaurant.getTables()) {
            if (dtForSaving.getStatus() == DomainObjectStatus.DELETED) {
                dbBroker.delete(dtForSaving);
                continue;
            }
            if (diningTables.contains(dtForSaving)) {
                dbBroker.update(dtForSaving);
            } else {
                dbBroker.insert(dtForSaving);
            }

        }
    }

    private List<DiningTable> getRestaurantTables(Restaurant restaurant) throws SQLException {
        DiningTable t = new DiningTable();
        t.setRestaurant(restaurant);
        List<DomainObject> tables = dbBroker.getAll(t);

        List<DiningTable> diningTables = tables.stream().map(DiningTable.class::cast).collect(Collectors.toList());
        diningTables = diningTables.stream().peek(dn -> dn.setRestaurant(restaurant)).collect(Collectors.toList());
        return diningTables;
    }
}
