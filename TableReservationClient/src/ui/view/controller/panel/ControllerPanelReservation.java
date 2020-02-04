/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ui.coordinator.GUICoordinator;
import ui.view.components.table.TableModelDiningTables;
import ui.view.components.table.TableModelReservations;
import ui.view.panel.JPanelReservation;
import util.Cuisine;

/**
 *
 * @author jeca
 */
public class ControllerPanelReservation {

    private JPanelReservation panel;
    private static ControllerPanelReservation instance;
    private Restaurant restaurant;
    
    private Date date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    
    

    private ControllerPanelReservation() {
        
    }

    public static ControllerPanelReservation getInstance() {
        if (instance == null) {
            instance = new ControllerPanelReservation();
        }
        return instance;
    }

    public JPanelReservation getPanel() {
        initializePanel();
        return this.panel;
    }

    private void initializePanel() {
        panel = new JPanelReservation();
        addEventHandlers();
        prepareForm();
    }

    private void prepareForm() {
        lockFields();
        loadCuisines();
        loadTimes();
        initializeReservationsTable();
    }

    private void addEventHandlers() {
        this.panel.getJbtnFindTables().addActionListener(e -> onFindTablesButtonClicked());
        this.panel.getJbtnAddReservation().addActionListener(e -> onAddReservationButtonClicked());
        this.panel.getJbtnCreateReservations().addActionListener(e -> onCreateReservationsButtonClicked());
        this.panel.getJbtnCancel().addActionListener(e -> onCancelButtonClicked(e));
        this.panel.getJbtnDeleteReservation().addActionListener(e->onDeleteReservationButtonClicked());
    }

    private void onFindTablesButtonClicked() {
        try {
            date = panel.getDateChooserCombo().getSelectedDate().getTime();
            timeFrom = getTime(panel.getJcboxTimeFrom());
            timeTo = getTime(panel.getJcboxTimeTo());
            List<DiningTable> freeTables = BLController.getInstance().findFreeTables(this.restaurant, date, timeFrom, timeTo);
            
            if (freeTables.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nema slobodnih stolova u trazenom terminu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
            }
//            Restaurant restaurant = new Restaurant();
//            restaurant.getTables().addAll(freeTables);
//            panel.getjTableDiningTables().setModel(new TableModelDiningTables(restaurant));
            ((TableModelDiningTables)panel.getjTableDiningTables().getModel()).setTables(freeTables);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCreateReservationsButtonClicked() {
        List<Reservation> reservations = ((TableModelReservations)panel.getjTableReservations().getModel()).getReservations();
        Map<String, Object> map = BLController.getInstance().saveReservations(reservations);
        List<Reservation> successfulReservations = (List<Reservation>) map.get("successfulReservations");
        List<Reservation> rejectedReservations = (List<Reservation>) map.get("rejectedReservations");
        GUICoordinator.getInstance().showSavedReservations(successfulReservations, rejectedReservations);
        resetForm();
    }

    private void onCancelButtonClicked(ActionEvent e) {
        closeDialog(e);
    }

    private void onAddReservationButtonClicked() {
        try {
            DiningTable chosenTable = getSelectedDiningTable();
            Reservation reservation = new Reservation(chosenTable, null, date, timeFrom, timeTo, false);
            TableModelReservations model = (TableModelReservations) panel.getjTableReservations().getModel();
            model.addReservation(reservation);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private DiningTable getSelectedDiningTable() throws Exception {
        int rowSelected = this.panel.getjTableDiningTables().getSelectedRow();
        if (rowSelected == -1) {
            throw new Exception("Morate izabrati sto.");
        }
        TableModelDiningTables model = (TableModelDiningTables) this.panel.getjTableDiningTables().getModel();
        return model.getDiningTable(rowSelected);
    }

    private void initializeReservationsTable() {
        TableModelReservations model = new TableModelReservations();
        panel.getjTableReservations().setModel(model);
    }

    private void loadCuisines() {
        ComboBoxModel model = new DefaultComboBoxModel(Cuisine.values());
        this.panel.getJcboxCuisine().setModel(model);
    }

    public void showRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        panel.getJtxtName().setText(restaurant.getName());
        panel.getJtxtAdress().setText(restaurant.getAdress());
        panel.getJtxtTIN().setText(restaurant.getTaxIdNumber());
        panel.getJcboxCuisine().setSelectedItem(Cuisine.valueOf(restaurant.getCuisine()));
        panel.getJcboxNonSmoking().setSelected(restaurant.isNonSmoking());
        panel.getJcboxPetsAllowed().setSelected(restaurant.isPetsAllowed());
        initializeDiningTablesTable();
    }

    private void initializeDiningTablesTable() {
        panel.getjTableDiningTables().setModel(new TableModelDiningTables(new Restaurant()));
    }

    private void lockFields() {
        panel.getJtxtName().setEnabled(false);
        panel.getJtxtAdress().setEnabled(false);
        panel.getJtxtTIN().setEnabled(false);
        panel.getJcboxCuisine().setEnabled(false);
        panel.getJcboxNonSmoking().setEnabled(false);
        panel.getJcboxPetsAllowed().setEnabled(false);
    }

    private void closeDialog(ActionEvent e) {
        Component component = (Component) e.getSource();
        JDialog dialog = (JDialog) SwingUtilities.getRoot(component);
        dialog.dispose();
    }

    private LocalTime getTime(JComboBox field) throws Exception {
        String timeString = field.getSelectedItem().toString();
        return LocalTime.parse(timeString);       
    }
    
    public List<String> getTimes(){
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("00:00");
        List<String> times = new LinkedList<>();
        while(start.equals(end) == false){
            times.add(start.toString());
            start = start.plusMinutes(30);
        }
        return times;
    }

    private void loadTimes() {
        this.panel.getJcboxTimeFrom().setModel(new DefaultComboBoxModel(getTimes().toArray()));
        this.panel.getJcboxTimeTo().setModel(new DefaultComboBoxModel(getTimes().toArray()));
    }

    private void onDeleteReservationButtonClicked() {
        try {
            Reservation chosenReservation = getSelectedReservation();
            TableModelReservations model = (TableModelReservations) panel.getjTableReservations().getModel();
            model.removeReservation(chosenReservation);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Reservation getSelectedReservation() throws Exception {
        int rowSelected = this.panel.getjTableReservations().getSelectedRow();
        if (rowSelected == -1) {
            throw new Exception("Morate izabrati sto.");
        }
        TableModelReservations model = (TableModelReservations) this.panel.getjTableReservations().getModel();
        return model.getReservation(rowSelected);
    }
    
    public void resetForm(){
        this.panel.getDateChooserCombo().setSelectedDate(Calendar.getInstance());
        this.loadTimes();
        ((TableModelDiningTables)this.panel.getjTableDiningTables().getModel()).removeTables();
        ((TableModelReservations)this.panel.getjTableReservations().getModel()).removeReservations();
    }
}
