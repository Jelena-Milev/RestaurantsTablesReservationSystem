/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import exception.CommunicationException;
import exception.ValidationException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import ui.coordinator.GUICoordinator;
import ui.view.panel.JPanelRegister;
import util.FieldLabelPair;

/**
 *
 * @author jeca
 */
public class ControllerPanelRegister {

    private static ControllerPanelRegister instance;
    private JPanelRegister panel;
    private List<FieldLabelPair> fieldLabelPairs;

    private ControllerPanelRegister() {
        
    }

    public static ControllerPanelRegister getInstance() {
        if (instance == null) {
            instance = new ControllerPanelRegister();
        }
        return instance;
    }

    public JPanelRegister getPanel() {
        initializePanel();
        return panel;
    }

    private void initializePanel() {
        panel = new JPanelRegister();
        addEventHandlers();
        initializeFieldLabelPairs();
    }

    private void addEventHandlers() {
        this.panel.getJbtnRegister().addActionListener(e -> onRegisterButtonClicked());
    }

    private void onRegisterButtonClicked() {
        try {
            validation(fieldLabelPairs);
            BLController.getInstance().register(panel.getJtxtUsername().getText(), String.valueOf(panel.getJtxtPassword().getPassword()), panel.getJtxtName().getText(), panel.getJtxtLastname().getText(), panel.getJtxtMail().getText());
            JOptionPane.showMessageDialog(panel, "Uspesna registracija. Mozete se prijaviti.");
            GUICoordinator.getInstance().successfulRegistration();
        } catch (ValidationException ex) {

        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
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

    private void initializeFieldLabelPairs() {
        fieldLabelPairs = new LinkedList() {
            {
                add(new FieldLabelPair(panel.getJtxtUsername(), panel.getJlblUsernameError(), "korisnicko ime"));
                add(new FieldLabelPair(panel.getJtxtPassword(), panel.getJlblPasswordError(), "lozinka"));
                add(new FieldLabelPair(panel.getJtxtName(), panel.getJlblNameError(), "ime"));
                add(new FieldLabelPair(panel.getJtxtLastname(), panel.getJlblLastnameError(), "prezime"));
                add(new FieldLabelPair(panel.getJtxtMail(), panel.getJlblMailError(), "e-mail"));
            }
        };
    }
}
