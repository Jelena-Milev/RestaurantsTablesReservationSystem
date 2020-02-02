/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import domain.DiningTable;
import java.awt.Window;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import ui.view.panel.JPanelDiningTable;
import util.DomainObjectStatus;
import util.FieldLabelPair;
import util.TablePosition;

/**
 *
 * @author jeca
 */
public class ControllerPanelDiningTable {
    
    private static ControllerPanelDiningTable instance;
    private JPanelDiningTable panel;
    private DiningTable table;

    private ControllerPanelDiningTable() {
        initializePanel();
        addEventHandlers();
        prepareForm();
    }

    public void initializePanel() {
        if (panel == null) {
            panel = new JPanelDiningTable();
        }
    }

    public static ControllerPanelDiningTable getInstance() {
        if (instance == null) {
            instance = new ControllerPanelDiningTable();
        }
        return instance;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setTable(DiningTable table) {
        this.table = table;
    }
    
    private void addEventHandlers() {
        this.panel.getJbtnSave().addActionListener(e -> onSaveButtonClicked());
    }    
    
    private void prepareForm() {
        loadPositions();
    }
    
    private void loadPositions() {
        ComboBoxModel model = new DefaultComboBoxModel(TablePosition.values());
        this.panel.getJcboxPosition().setModel(model);
    }
    
    private int validateInt(FieldLabelPair pair) throws Exception {
        pair.getLabel().setText("");
        try {
            int numberOfPeople = Integer.parseInt(pair.getField().getText().trim());
            return numberOfPeople;
        } catch (NumberFormatException ex) {
            pair.getLabel().setText("Morate uneti cifru");
            throw new Exception("Neispravan unos");
        }
    }

    public void showTable() {
        this.panel.getJtxtNumberOfPeople().setText(table.getNumberOfPeople()+"");
        this.panel.getJcboxPosition().setSelectedItem(TablePosition.valueOf(table.getPosition()));
    }

    private void onSaveButtonClicked() {
        try {
            int numberOfTables = validateInt(new FieldLabelPair(panel.getJtxtNumberOfPeople(), panel.getJlblErrorNumOfPeople(), "broj osoba"));
            String position = panel.getJcboxPosition().getSelectedItem().toString();
            table.setNumberOfPeople(numberOfTables);
            table.setPosition(position);
            table.setStatus(DomainObjectStatus.ACTIVE);
            this.closeDialog();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void closeDialog(){
        Window w = SwingUtilities.getWindowAncestor(panel);
        w.dispose();
    }
}
