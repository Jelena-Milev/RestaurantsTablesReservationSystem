/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import logic.systemOperation.SystemOperation;
import domain.DiningTable;
import domain.object.DomainObject;
import domain.Restaurant;
import java.util.List;
import java.util.stream.Collectors;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class SOSaveRestaurant extends SystemOperation {

    public SOSaveRestaurant(DomainObject odo) {
        this.odo = odo;
        //validator
    }

    @Override
    protected void operation() throws Exception {
        List<DomainObject> restaurants = dbBroker.get(odo);
        if (restaurants.isEmpty()) {
            Long id = dbBroker.insert(odo);
            Restaurant restaurant = (Restaurant) odo;
            for (DiningTable table : restaurant.getTables()) {
                table.getRestaurant().setId(id);
                dbBroker.insert(table);
            }
        } else {
            dbBroker.update(odo);
            DiningTable t = new DiningTable();
            t.setRestaurant((Restaurant) odo);
            
            List<DomainObject> tablesInDatabase = dbBroker.get(t);
            tablesInDatabase = tablesInDatabase.stream().peek(DiningTable.class::cast).collect(Collectors.toList());
            
            for (DiningTable dtForSaving : ((Restaurant) odo).getTables()) {
                if (dtForSaving.getStatus() == DomainObjectStatus.DELETED) {
                    dbBroker.delete(dtForSaving);
                } else {
                    if (tablesInDatabase.contains(dtForSaving)) {
                        dbBroker.update(dtForSaving);
                    } else {
                        dbBroker.insert(dtForSaving);
                    }
                }
            }
        }

    }

}
