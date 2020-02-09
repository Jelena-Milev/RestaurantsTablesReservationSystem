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
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import ui.coordinator.GUICoordinator;
import ui.view.components.table.TableModelDiningTables;
import ui.view.panel.JPanelRestaurant;
import util.Cuisine;
import util.FieldLabelPair;
import util.RestaurantPanelMode;
import util.TablePosition;
import validator.Validator;
import validator.impl.ValidatorImplementation;

/**
 *
 * @author jeca
 */
public class ControllerPanelRestaurant {

    private static ControllerPanelRestaurant instance;

    private JPanelRestaurant panel;
    private RestaurantPanelMode mode;
    private Restaurant thisRestaurant;
    private final Validator validator;

    private ControllerPanelRestaurant() {
        validator = new ValidatorImplementation();
    }

    public static ControllerPanelRestaurant getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRestaurant();
        }
        return instance;
    }

    public JPanel getPanel(RestaurantPanelMode mode) {
        initializePanel(mode);
        return panel;
    }

    private void initializePanel(RestaurantPanelMode mode1) {
        panel = new JPanelRestaurant();
        this.mode = mode1;
        adjustPanel();
        addEventHandlers();
        prepareForm();
        initializeFieldLabelPairs();
    }

    public void showRestaurant(Restaurant restaurant) {
        thisRestaurant = restaurant;
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
        panel.getJbtnSaveRestaurant().addActionListener(e -> onSaveRestaurantButtonClicked(e));
        panel.getJbtnCancel().addActionListener(e -> onCancelButtonClicked(e));
        panel.getJbtnDeactivateRestaurant().addActionListener(e -> onDeactivateRestaurantClicked(e));
    }

    private void onAddTableButtonClicked() {
        TableModelDiningTables model = (TableModelDiningTables) panel.getJTableDiningTables().getModel();
        try {
            //validiraj label
            int numberOfTables = validateDiningTableData();
            String label = panel.getJtxtTableLabel().getText();
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
            throw new Exception("Morate izabrati sto.");
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
        this.mode = RestaurantPanelMode.UPDATE;
        this.adjustPanel();
    }

    private void onSaveRestaurantButtonClicked(ActionEvent e) {
        TableModelDiningTables model = (TableModelDiningTables) panel.getJTableDiningTables().getModel();
        try {
            this.validateRestaurantHeader();

            model.setRestaurantFields(panel.getJtxtName().getText().trim(), panel.getJtxtTIN().getText().trim(), panel.getJtxtAdress().getText().trim(), panel.getJcboxNonSmoking().isSelected(), panel.getJcboxPetsAllowed().isSelected(), panel.getJcboxCuisine().getSelectedItem().toString());
            model.mergeTables();
            if (mode == RestaurantPanelMode.ADD) {
                BLController.getInstance().saveRestaurant(model.getRestaurant());
                JOptionPane.showMessageDialog(panel, "Uspesno kreiran restoran", "Dodavanje restorana", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } else if (mode == RestaurantPanelMode.UPDATE) {
                BLController.getInstance().updateRestaurant(model.getRestaurant());
                JOptionPane.showMessageDialog(panel, "Uspesno sacuvan restoran", "Izmena restorana", JOptionPane.INFORMATION_MESSAGE);
                closeDialog(e);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska pri cuvanju restorana", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancelButtonClicked(ActionEvent e) {
        closeDialog(e);
    }

    private void adjustPanel() {
        adjustRestaurantFields(this.mode);
        adjustDiningTableFields(this.mode);
        adjustDiningTableButtons(this.mode);
        adjustRestaurantButtons(this.mode);
    }

    private void adjustRestaurantButtons(RestaurantPanelMode mode) {
        if (mode == RestaurantPanelMode.VIEW) {
            panel.getJbtnSaveRestaurant().setVisible(false);
            panel.getJbtnChangeRestaurant().setVisible(true);
            panel.getJbtnDeactivateRestaurant().setVisible(true);
            return;
        }
        panel.getJbtnSaveRestaurant().setVisible(true);
        panel.getJbtnChangeRestaurant().setVisible(false);
        panel.getJbtnDeactivateRestaurant().setVisible(false);

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

    private List initializeFieldLabelPairs() {
        return new LinkedList() {
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

    private void closeDialog(ActionEvent e) {
        Component component = (Component) e.getSource();
        JDialog dialog = (JDialog) SwingUtilities.getRoot(component);
        dialog.dispose();
    }

    public void addListenerForClosingDialogEvent() {
        Window window = SwingUtilities.getWindowAncestor(panel);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Restaurant panel closed");
                GUICoordinator.getInstance().refreshRestaurantSearchTable();
            }
        });
    }

    private int validateDiningTableData() throws ValidationException {
        validator.validateStringEmpty(new FieldLabelPair(panel.getJtxtTableLabel(), panel.getJlblErrorTableLabel(), "oznaka stola"));
        int numOfPeople = validator.validateInt(new FieldLabelPair(panel.getJtxtNumberOfPeople(), panel.getJlblErrorNumOfPeople(), "broj osoba"));
        return numOfPeople;
    }

    private void validateRestaurantHeader() throws ValidationException {
        List<FieldLabelPair> fieldLabelPairs = initializeFieldLabelPairs();
        validator.validateStringsEmpty(fieldLabelPairs);
    }

    private void onDeactivateRestaurantClicked(ActionEvent e) {
        int answer = JOptionPane.showConfirmDialog(panel, "Da li ste sigurni da zelite da deaktivirate restoran?", "Deaktivacija restorana", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                BLController.getInstance().deactivateRestaurant(thisRestaurant);
                JOptionPane.showMessageDialog(null, "Restoran je uspesno deaktiviran", "Deaktivacija restorana", JOptionPane.INFORMATION_MESSAGE);
                closeDialog(e);
            } catch (CommunicationException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska pri deaktivaciji restorana", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
