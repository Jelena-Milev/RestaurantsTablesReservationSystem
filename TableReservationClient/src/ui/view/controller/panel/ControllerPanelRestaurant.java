/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import domain.DiningTable;
import domain.Restaurant;
import exception.CommunicationException;
import exception.ValidationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import service.CommunicationService;
import ui.coordinator.GUICoordinator;
import ui.view.components.table.TableModelDiningTables;
import ui.view.components.table.TableModelRestaurants;
import ui.view.panel.JPanelRestaurant;
import util.Cuisine;
import util.FieldLabelPair;
import util.RestaurantPanelMode;
import util.TablePosition;

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurant {

    private static ControllerPanelRestaurant instance;

    private JPanelRestaurant panel;

    private List<FieldLabelPair> fieldLabelPairs;

    private ControllerPanelRestaurant() {
        initializePanel();
        addEventHandlers();
        prepareForm();
        initializeFieldLabelPairs();
    }

    public static ControllerPanelRestaurant getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurant();
        }
        return instance;
    }

    private void initializePanel() {
        if (panel == null) {
            panel = new JPanelRestaurant();
        }
    }

    public JPanel getPanel(RestaurantPanelMode mode) {
        adjustPanel(mode);
        return panel;
    }

    public void showRestaurant(Restaurant restaurant) {
        panel.getJtxtName().setText(restaurant.getName());
        panel.getJtxtAdress().setText(restaurant.getAdress());
        panel.getJtxtTIN().setText(restaurant.getTaxIdNumber());
        panel.getJcboxCuisine().setSelectedItem(Cuisine.valueOf(restaurant.getCuisine()));
        panel.getJcboxNonSmoking().setSelected(restaurant.isNonSmoking());
        panel.getJcboxPetsAllowed().setSelected(restaurant.isPetsAllowed());
        panel.getJTableDiningTables().setModel(new TableModelDiningTables(restaurant));
    }

    private void prepareForm() {
        loadCuisines();
        loadPositions();
        initializeDiningTablesTable();
    }

    private void loadCuisines() {
        ComboBoxModel model = new DefaultComboBoxModel(Cuisine.values());
        this.panel.getJcboxCuisine().setModel(model);
    }

    private void initializeDiningTablesTable() {
        Restaurant restaurant = new Restaurant();
        panel.getJTableDiningTables().setModel(new TableModelDiningTables(restaurant));
    }

    private void loadPositions() {
        ComboBoxModel model = new DefaultComboBoxModel(TablePosition.values());
        this.panel.getJcboxPosition().setModel(model);
    }

    private void addEventHandlers() {
        panel.getJbtnAddDiningTable().addActionListener(e -> onAddTableButtonClicked());
        panel.getJbtnRemoveDiningTable().addActionListener(e -> onRemoveTableButtonClicked());
        panel.getJbtnUpdateDinningTable().addActionListener(e -> onUpdateTableButtonClicked());
        panel.getJbtnChangeRestaurant().addActionListener(e -> onChangeRestaurantButtonClicked());
        panel.getJbtnSaveRestaurant().addActionListener(e -> onSaveRestaurantButtonClicked());
        panel.getJbtnCancel().addActionListener(e -> onCancelButtonClicked());
    }

    private void adjustPanel(RestaurantPanelMode mode) {
        adjustRestaurantFields(mode);
        adjustDiningTableFields(mode);
        adjustDiningTableButtons(mode);
        adjustRestaurantButtons(mode);
    }

    private void adjustRestaurantButtons(RestaurantPanelMode mode) {
        if (mode == RestaurantPanelMode.VIEW) {
            panel.getJbtnSaveRestaurant().setVisible(false);
            panel.getJbtnChangeRestaurant().setVisible(true);
            return;
        }
        panel.getJbtnSaveRestaurant().setVisible(true);
        panel.getJbtnChangeRestaurant().setVisible(false);
    }

    private void adjustDiningTableButtons(RestaurantPanelMode mode) {
        if (mode == RestaurantPanelMode.VIEW) {
            panel.getJbtnAddDiningTable().setVisible(false);
            panel.getJbtnRemoveDiningTable().setVisible(false);
            panel.getJbtnUpdateDinningTable().setVisible(false);
            return;
        }
        panel.getJbtnAddDiningTable().setVisible(true);
        panel.getJbtnRemoveDiningTable().setVisible(true);
        panel.getJbtnUpdateDinningTable().setVisible(true);
    }

    private void adjustDiningTableFields(RestaurantPanelMode mode) {
        if (mode == RestaurantPanelMode.VIEW) {
            panel.getJtxtTableLabel().setEnabled(false);
            panel.getJtxtNumberOfPeople().setEnabled(false);
            panel.getJcboxPosition().setEnabled(false);
            return;
        }
        panel.getJtxtTableLabel().setEnabled(true);
        panel.getJtxtNumberOfPeople().setEnabled(true);
        panel.getJcboxPosition().setEnabled(true);
    }

    private void adjustRestaurantFields(RestaurantPanelMode mode) {
        if (mode == RestaurantPanelMode.VIEW) {
            panel.getJtxtName().setEnabled(false);
            panel.getJtxtAdress().setEnabled(false);
            panel.getJtxtTIN().setEnabled(false);
            panel.getJcboxCuisine().setEnabled(false);
            panel.getJcboxNonSmoking().setEnabled(false);
            panel.getJcboxPetsAllowed().setEnabled(false);
        } else if (mode == RestaurantPanelMode.ADD) {
            panel.getJtxtName().setEnabled(true);
            panel.getJtxtAdress().setEnabled(true);
            panel.getJtxtTIN().setEnabled(true);
            panel.getJcboxCuisine().setEnabled(true);
            panel.getJcboxNonSmoking().setEnabled(true);
            panel.getJcboxPetsAllowed().setEnabled(true);
        } else {
            panel.getJtxtName().setEnabled(false);
            panel.getJtxtAdress().setEnabled(true);
            panel.getJtxtTIN().setEnabled(false);
            panel.getJcboxCuisine().setEnabled(true);
            panel.getJcboxNonSmoking().setEnabled(true);
            panel.getJcboxPetsAllowed().setEnabled(true);
        }
    }

    private void initializeFieldLabelPairs() {
        this.fieldLabelPairs = new LinkedList() {
            {
                add(new FieldLabelPair(panel.getJtxtName(), panel.getJlblErrorName(), "naziv"));
                add(new FieldLabelPair(panel.getJtxtTIN(), panel.getJlblErrorTIN(), "PIB"));
                add(new FieldLabelPair(panel.getJtxtAdress(), panel.getJlblErrorAdress(), "adresa"));
            }
        };
    }

    private void resetForm() {
        panel.getJtxtName().setText("");
        panel.getJtxtAdress().setText("");
        panel.getJtxtTIN().setText("");
        panel.getJtxtNumberOfPeople().setText("");
        panel.getJcboxCuisine().setSelectedIndex(0);
        panel.getJcboxPosition().setSelectedIndex(0);
        panel.getJcboxNonSmoking().setSelected(false);
        panel.getJcboxPetsAllowed().setSelected(false);
        this.initializeDiningTablesTable();
    }

    private void validation(List<FieldLabelPair> fieldLabelPairs) throws ValidationException {
        for (FieldLabelPair fieldLabelPair : fieldLabelPairs) {
            fieldLabelPair.getLabel().setText("");
            if (fieldLabelPair.getField().getText().isEmpty()) {
                fieldLabelPair.getLabel().setText("Morate uneti " + fieldLabelPair.getFieldName());
            }
        }
        if (fieldLabelPairs.stream().anyMatch(pair -> pair.getField().getText().isEmpty())) {
            throw new ValidationException("Polje ne sme biti prazno");
        }
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

    

    private void onAddTableButtonClicked() {
        TableModelDiningTables model = (TableModelDiningTables) panel.getJTableDiningTables().getModel();
        try {
            //validiraj label
            String label = panel.getJtxtTableLabel().getText();
            int numberOfTables = validateInt(new FieldLabelPair(panel.getJtxtNumberOfPeople(), panel.getJlblErrorNumOfPeople(), "broj osoba"));
            String position = panel.getJcboxPosition().getSelectedItem().toString();

            model.addDiningTable(label, numberOfTables, position);
            resetDiningTableFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DiningTable getSelectedDiningTable() throws Exception {
        int rowSelected = this.panel.getJTableDiningTables().getSelectedRow();
        if (rowSelected == -1) {
            throw new Exception("Morate izabrati restoran.");
        }
        TableModelDiningTables model = (TableModelDiningTables) this.panel.getJTableDiningTables().getModel();
        return model.getDiningTable(rowSelected);
    }
    
    private void resetDiningTableFields() {
        panel.getJtxtTableLabel().setText("");
        panel.getJtxtNumberOfPeople().setText("");
        panel.getJcboxPosition().setSelectedIndex(0);
    }

    private void onRemoveTableButtonClicked() {
        try {
            DiningTable diningTable = getSelectedDiningTable();
            TableModelDiningTables model = (TableModelDiningTables) panel.getJTableDiningTables().getModel();
            model.removeDiningTable(diningTable);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onUpdateTableButtonClicked() {
        TableModelDiningTables model = (TableModelDiningTables) this.panel.getJTableDiningTables().getModel();
        try {
            DiningTable table = getSelectedDiningTable();
            GUICoordinator.getInstance().changeTable(table);
            model.fireTableDataChanged();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onChangeRestaurantButtonClicked() {
        this.adjustPanel(RestaurantPanelMode.UPDATE);
    }

    private void onSaveRestaurantButtonClicked() {
        TableModelDiningTables model = (TableModelDiningTables) panel.getJTableDiningTables().getModel();
        try {
            this.validation(this.fieldLabelPairs);

            model.setRestaurant(panel.getJtxtName().getText().trim(), panel.getJtxtTIN().getText().trim(), panel.getJtxtAdress().getText().trim(), panel.getJcboxNonSmoking().isSelected(), panel.getJcboxPetsAllowed().isSelected(), panel.getJcboxCuisine().getSelectedItem().toString());
            model.mergeTables();
            BLController.getInstance().saveRestaurant(model.getRestaurant());
            JOptionPane.showMessageDialog(panel, "Uspesno sacuvan restoran", "Dodavanje restorana", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska pri cuvanju restorana", JOptionPane.ERROR_MESSAGE);
        }
        resetForm();
    }

    private void onCancelButtonClicked() {
        throw new UnsupportedOperationException("Jelena, napravi cancel dugme da radi."); //To change body of generated methods, choose Tools | Templates.
    }
}
