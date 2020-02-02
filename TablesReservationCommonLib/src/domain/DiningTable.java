/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.object.DomainObject;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class DiningTable extends DomainObject implements Serializable, Comparable<DiningTable> {

    private String label;
    private int numberOfPeople;
    private String position;
    private Restaurant restaurant;
    private DomainObjectStatus active;

    public DiningTable() {
    }

    public DiningTable(String label, Long restaurantId) {
        this.label = label;
        this.restaurant = new Restaurant();
        restaurant.setId(restaurantId);
    }

    public DiningTable(String label, int numberOfPeople, String position, Restaurant restaurant) {
        this.label = label;
        this.numberOfPeople = numberOfPeople;
        this.position = position;
        this.restaurant = restaurant;
    }

    public DiningTable(String label, int numberOfPeople, String position, Restaurant restaurant, DomainObjectStatus status) {
        this.label = label;
        this.numberOfPeople = numberOfPeople;
        this.position = position;
        this.restaurant = restaurant;
        this.active = status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        return active;
    }

    public void setStatus(DomainObjectStatus status) {
        this.active = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiningTable other = (DiningTable) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.restaurant, other.restaurant)) {
            return false;
        }
        return true;
    }

    @Override
    public String getAllColumnNames() {
        return "label, numberOfPeople, position, restaurantId, active";
    }

    @Override
    public String getInsertColumnNames() {
        return "label, numberOfPeople, position, restaurantId, active";
    }

    @Override
    public String getSelectWhereClause() {
        return String.format("restaurantId = %d AND active = \"%s\"", restaurant.getId(), DomainObjectStatus.ACTIVE);
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
                String label = rs.getString("label");
                int numOfPeople = rs.getInt("numberOfPeople");
                String position = rs.getString("position");
                String active = rs.getString("active");

                DiningTable table = new DiningTable(label, numOfPeople, position, null, DomainObjectStatus.valueOf(active));

                tables.add(table);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tables;
    }

    @Override
    public String getColumnValues() {
        return String.format("\"%s\", %d, \"%s\", %d, \"%s\"", label, numberOfPeople, position, restaurant.getId(), active);
    }

    @Override
    public String getUpdateClause() {
        return String.format("numberOfPeople = %d, position = \"%s\", active = \"%s\"", numberOfPeople, position, active);
    }

    @Override
    public String getUpdateWhereClause() {
        return String.format("label = \"%s\" AND restaurantId = %d ", label, restaurant.getId());
    }

    @Override
    public int compareTo(DiningTable o) {
        return this.getLabel().compareTo(o.getLabel());
    }

    @Override
    public String getDeleteClause() {
        return String.format("active = \"%s\"", DomainObjectStatus.DELETED.toString());
    }

    @Override
    public String getDeleteWhereClause() {
        return String.format("label = \"%s\" AND restaurantId = %d ", label, restaurant.getId());
    }

    @Override
    public String getSelectAllWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
