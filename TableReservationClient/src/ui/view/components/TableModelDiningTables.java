/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.components;

import domain.DiningTable;
import domain.Restaurant;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jeca
 */
public class TableModelDiningTables extends AbstractTableModel {
    
    private final String[] columnNames = {"Rbr", "Broj osoba", "Pozicija"};
    private final Class[] columnClasses = {Long.class, Integer.class, String.class};
    private Restaurant restaurant;

    public TableModelDiningTables(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public int getRowCount() {
        return restaurant.getTables().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DiningTable diningTable = restaurant.getTables().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return diningTable.getId();
            case 1:
                return diningTable.getNumberOfPeople();
            case 2:
                return diningTable.getPosition();
            default:
                return "N/A";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public void addDiningTable(int numberOfPeople, String position) throws Exception{
        DiningTable diningTable = new DiningTable();
        diningTable.setId(new Long(restaurant.getTables().size()+1));
        diningTable.setNumberOfPeople(numberOfPeople);
        diningTable.setPosition(position);
        diningTable.setRestaurant(restaurant);
        this.restaurant.getTables().add(diningTable);
        fireTableDataChanged();
    }
    
    public void removeDiningTable(int rowIndex){
        this.restaurant.getTables().remove(rowIndex);
        setIdNumbers();
        fireTableDataChanged();
    }
    
    public List<DiningTable> getDiningTables(){
        setIdNumbers();
        return this.restaurant.getTables();
    }

    private void setIdNumbers() {
        long no = 0;
        for (DiningTable table : restaurant.getTables()) {
            table.setId(new Long(++no));
        }
    }
    
    public void setRestaurant(String name, String tin, String adress, boolean nonSmoking, boolean petsAllowed, String cuisine) throws Exception{
        this.restaurant.setName(name);
        this.restaurant.setTaxIdNumber(tin);
        this.restaurant.setAdress(adress);
        this.restaurant.setNonSmoking(nonSmoking);
        this.restaurant.setPetsAllowed(petsAllowed);
        this.restaurant.setCuisine(cuisine);        
        this.restaurant.setDateAdded(new Date());
    }
    
    public Restaurant getRestaurant(){
        return this.restaurant;
    }
}
