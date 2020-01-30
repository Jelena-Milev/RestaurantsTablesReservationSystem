/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import util.ItemStatus;

/**
 *
 * @author jeca
 */
public class DiningTable extends DomainObject implements Serializable{
    
    private Long id;
    private int numberOfPeople;
    private String position;
    private Restaurant restaurant;
    private ItemStatus status;

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
        if(numberOfPeople < 2 || numberOfPeople > 12)
            throw new Exception("Broj osoba ne moze biti manji od 2 ili veci od 12.");
        this.numberOfPeople = numberOfPeople;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
    
    @Override
    public String getAllColumnNames() {
        return "id, numberOfPeople, position, restaurantId";
    }

    @Override
    public String getInsertColumnNames() {
        return "id, numberOfPeople, position, restaurantId";
    }

    @Override
    public String getSelectWhereClause() {
        return "id = "+id+", restaurantId = "+restaurant.getId();
    }

    @Override
    public String getTableName() {
        return "DiningTable";
    }

    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return getSelectWhereClause();
    }   
}
