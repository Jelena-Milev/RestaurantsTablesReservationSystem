/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.components.table;

import domain.DiningTable;
import domain.Restaurant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.DomainObjectStatus;

/**
 *
 * @author jeca
 */
public class TableModelDiningTables extends AbstractTableModel {

    private final String[] columnNames = {"Oznaka", "Broj osoba", "Pozicija"};
    private final Class[] columnClasses = {String.class, Integer.class, String.class};
    private Restaurant restaurant;
    private List temporaryTables;
    private List forDeleting;

    public TableModelDiningTables(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.temporaryTables = new LinkedList();
        this.forDeleting = new LinkedList();
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
                return diningTable.getLabel();
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

    public void addDiningTable(String label, int numberOfPeople, String position) throws Exception {
        DiningTable diningTable = new DiningTable(label, numberOfPeople, position, restaurant, DomainObjectStatus.ACTIVE);
        if (this.restaurant.getTables().contains(diningTable) || this.forDeleting.contains(diningTable)) {
            throw new Exception("Oznaka stola vec postoji.");
        }
        this.restaurant.getTables().add(diningTable);
        this.temporaryTables.add(diningTable);
        fireTableDataChanged();
    }

    public void removeDiningTable(DiningTable table) {
        if (this.restaurant.getTables().contains(table) && !temporaryTables.contains(table)) {
            table.setStatus(DomainObjectStatus.DELETED);
            forDeleting.add(table);
            this.restaurant.getTables().remove(table);
        } else if (this.restaurant.getTables().contains(table) && temporaryTables.contains(table)) {
            this.restaurant.getTables().remove(table);
            this.temporaryTables.remove(table);
        }
        fireTableDataChanged();
    }

    public void setRestaurantFields(String name, String tin, String adress, boolean nonSmoking, boolean petsAllowed, String cuisine) throws Exception {
        this.restaurant.setName(name);
        this.restaurant.setTaxIdNumber(tin);
        this.restaurant.setAdress(adress);
        this.restaurant.setNonSmoking(nonSmoking);
        this.restaurant.setPetsAllowed(petsAllowed);
        this.restaurant.setCuisine(cuisine);
        if (this.restaurant.getDateAdded() == null) {
            this.restaurant.setDateAdded(new Date());
        }
        this.restaurant.setStatus(DomainObjectStatus.ACTIVE);
    }

    public void updateRestaurant(String adress, boolean nonSmoking, boolean petsAllowed, String cuisine) throws Exception {
        this.restaurant.setAdress(adress);
        this.restaurant.setNonSmoking(nonSmoking);
        this.restaurant.setPetsAllowed(petsAllowed);
        this.restaurant.setCuisine(cuisine);
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public DiningTable getDiningTable(int rowSelected) {
        return restaurant.getTables().get(rowSelected);
    }

    public void mergeTables() {
        this.restaurant.getTables().addAll(forDeleting);
    }

    public void setTables(List<DiningTable> freeTables) {
        this.restaurant.setTables(freeTables);
        fireTableDataChanged();
    }
    
    public void removeTables(){
        this.restaurant.getTables().removeAll(this.restaurant.getTables());
        fireTableDataChanged();
    }
}
