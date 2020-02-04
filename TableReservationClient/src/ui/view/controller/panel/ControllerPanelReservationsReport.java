/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import domain.Reservation;
import java.util.List;
import ui.view.components.table.TableModelReservations;
import ui.view.panel.JPanelReservationsReport;

/**
 *
 * @author jeca
 */
public class ControllerPanelReservationsReport {

    private JPanelReservationsReport panel;
    private static ControllerPanelReservationsReport instance;
    private List<Reservation> successfulReservations;
    private List<Reservation> rejectedReservations;

    private ControllerPanelReservationsReport() {

    }

    public static ControllerPanelReservationsReport getInstance() {
        if (instance == null) {
            instance = new ControllerPanelReservationsReport();
        }
        return instance;
    }

    public JPanelReservationsReport getPanel(List<Reservation> successfulReservations, List<Reservation> rejectedReservations) {
        setData(successfulReservations, rejectedReservations);
        initializePanel();
        return this.panel;
    }

    private void initializePanel() {
        panel = new JPanelReservationsReport();
        initializeReservationsTables();
    }

    public void setData(List<Reservation> successfulReservations, List<Reservation> rejectedReservations) {
        this.successfulReservations = successfulReservations;
        this.rejectedReservations = rejectedReservations;
    }

    private void initializeReservationsTables() {
        TableModelReservations modelSavedReser = new TableModelReservations(successfulReservations);
        panel.getjTableSavedReservations1().setModel(modelSavedReser);
        TableModelReservations modelUnsavedReser = new TableModelReservations(rejectedReservations);
        panel.getjTableRejectedReservations().setModel(modelUnsavedReser);
    }
}
