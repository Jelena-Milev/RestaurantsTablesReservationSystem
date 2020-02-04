/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import domain.Reservation;
import exception.CommunicationException;
import java.util.List;
import javax.swing.JOptionPane;
import ui.view.components.table.TableModelReservations;
import ui.view.panel.JPanelAllReservations;

/**
 *
 * @author jeca
 */
public class ControllerPanelAllReservation {

    private JPanelAllReservations panel;
    private static ControllerPanelAllReservation instance;
    private List<Reservation> allReservations;

    private ControllerPanelAllReservation() {

    }

    public static ControllerPanelAllReservation getInstance() {
        if (instance == null) {
            instance = new ControllerPanelAllReservation();
        }
        return instance;
    }

    public JPanelAllReservations getPanel() {
        initializePanel();
        return this.panel;
    }

    private void initializePanel() {
        panel = new JPanelAllReservations();
        loadReservations();
        showReservations();
        addEventHandlers();
    }

    private void loadReservations() {
        //make it prettier
        try {
            allReservations = BLController.getInstance().getAllReservations();
        } catch (CommunicationException ex) {
            ex.printStackTrace();
        }
    }

    private void addEventHandlers() {
        this.panel.getJbtnCancelReservation().addActionListener(e -> onCancelReservationButtonClicked());
    }

    private void onCancelReservationButtonClicked() {
        try {
            Reservation reservation = getSelectedReservation();
            BLController.getInstance().cancelReservation(reservation);
            TableModelReservations model = (TableModelReservations) this.panel.getjTableReservations().getModel();
            model.reservationCanceled(reservation);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showReservations() {
        TableModelReservations model = new TableModelReservations(this.allReservations);
        panel.getjTableReservations().setModel(model);
    }

    private Reservation getSelectedReservation() throws Exception {
        int rowSelected = this.panel.getjTableReservations().getSelectedRow();
        if (rowSelected == -1) {
            throw new Exception("Morate izabrati rezervaciju.");
        }
        TableModelReservations model = (TableModelReservations) this.panel.getjTableReservations().getModel();
        return model.getReservation(rowSelected);
    }

}
