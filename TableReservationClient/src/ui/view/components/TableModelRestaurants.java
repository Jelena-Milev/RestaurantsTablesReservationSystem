/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.components;

import domain.Restaurant;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jeca
 */
public class TableModelRestaurants extends AbstractTableModel {

    private final String[] columnNames = {"Rbr", "Naziv", "Adresa", "Dozvoljeni ljubimci", "Nepušački", "Kuhinja"};
    private final Class[] columnClasses = {Integer.class, String.class, String.class, Boolean.class, Boolean.class, String.class};
    private List<Restaurant> restaurants;

    public TableModelRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public int getRowCount() {
        return restaurants.size();
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
        Restaurant restaurant = restaurants.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return restaurant.getName();
            case 2:
                return restaurant.getAdress();
            case 3:
                return restaurant.isPetsAllowed();
            case 4:
                return restaurant.isNonSmoking();
            case 5:
                return restaurant.getCuisine();
            default:
                return "N/A";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public void setRestaurants(List<Restaurant> restaurants){
        this.restaurants = restaurants;
        fireTableDataChanged();
    }
}
