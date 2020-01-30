/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class DiningTable extends DomainObject implements Serializable, Comparable<DiningTable> {

    private Long id;
    private int numberOfPeople;
    private String position;
    private Restaurant restaurant;
    private DomainObjectStatus status;

    public DiningTable() {
    }

    public DiningTable(Long id, int numberOfPeople, String position, Restaurant restaurant) {
        this.id = id;
        this.numberOfPeople = numberOfPeople;
        this.position = position;
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) throws Exception {
        if (numberOfPeople < 2 || numberOfPeople > 12) {
            throw new Exception("Broj osoba ne moze biti manji od 2 ili veci od 12.");
        }
        this.numberOfPeople = numberOfPeople;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public DomainObjectStatus getStatus() {
        return status;
    }

    public void setStatus(DomainObjectStatus status) {
        this.status = status;
    }

    @Override
    public String getAllColumnNames() {
        return "id, numberOfPeople, position, restaurantId, active";
    }

    @Override
    public String getInsertColumnNames() {
        return "id, numberOfPeople, position, restaurantId";
    }

    @Override
    public String getSelectWhereClause() {
        return "restaurantId = " + restaurant.getId();
    }

    @Override
    public String getTableName() {
        return "DiningTable";
    }

    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> tables = new ArrayList();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                int numOfPeople = rs.getInt("numberOfPeople");
                String position = rs.getString("position");
                boolean active = rs.getBoolean("active");

                DiningTable table = new DiningTable(id, numOfPeople, position, null);

                if (active) {
                    table.setStatus(DomainObjectStatus.ACTIVE);
                } else {
                    table.setStatus(DomainObjectStatus.DELETED);
                }

                tables.add(table);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tables;
    }

    @Override
    public String getColumnValues() {
        return String.format("%d, %d, \"%s\", %d", id, numberOfPeople, position, restaurant.getId());
    }

    @Override
    public String getUpdateClause() {
        return String.format("numberOfPeople = %d, position = \"%s\"", numberOfPeople, position);
    }

    @Override
    public String getUpdateWhereClause() {
        return "id = " + id + ", restaurantId = " + restaurant.getId();
    }

    @Override
    public int compareTo(DiningTable o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String getDeleteClause() {
        return "active = " + false;
    }

    @Override
    public String getDeleteWhereClause() {
        return "id = " + id + ", restaurantId = " + restaurant.getId();
    }
}
