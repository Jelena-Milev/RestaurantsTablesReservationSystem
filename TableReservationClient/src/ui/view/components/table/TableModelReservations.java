/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.components.table;

import domain.Reservation;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jeca
 */
public class TableModelReservations extends AbstractTableModel{
    
    private final String[] columnNames = {"Restoran", "Sto", "Datum", "Vreme od", "Vreme do", "Otkazana"};
    private final Class[] columnClasses = {String.class, String.class, Date.class, LocalTime.class, LocalTime.class, Boolean.class};
    private List<Reservation> reservations;

    public TableModelReservations() {
        reservations = new LinkedList<>();
    }
    
    @Override
    public int getRowCount() {
        return reservations.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation reservation = reservations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return reservation.getDiningTable().getRestaurant().getName();
            case 1:
                return reservation.getDiningTable().getLabel();
            case 2:
//                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");
//                return format.format(reservation.getDate());
                return reservation.getDate();
            case 3:
                return reservation.getTimeFrom();
            case 4:
                return reservation.getTimeTo();
            case 5:
                return reservation.isCanceled();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }   
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    
    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
        fireTableDataChanged();
    }
    
}
