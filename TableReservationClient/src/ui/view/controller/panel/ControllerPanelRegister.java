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
import validator.Validator;
import validator.impl.ValidatorImplementation;

/**
 *
 * @author jeca
 */
public class ControllerPanelRegister {

    private static ControllerPanelRegister instance;
    private JPanelRegister panel;
    
    private final Validator validator;

    private ControllerPanelRegister() {
        validator = new ValidatorImplementation();
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
            validation();
            BLController.getInstance().register(panel.getJtxtUsername().getText(), String.valueOf(panel.getJtxtPassword().getPassword()), panel.getJtxtName().getText(), panel.getJtxtLastname().getText(), panel.getJtxtMail().getText());
            JOptionPane.showMessageDialog(panel, "Uspesna registracija. Mozete se prijaviti.");
            GUICoordinator.getInstance().successfulRegistration();
        } catch (ValidationException ex) {

        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validation() throws ValidationException {
        List<FieldLabelPair> fieldLabelPairs = initializeFieldLabelPairs();
        validator.validateStringsEmpty(fieldLabelPairs);
        validator.validateStringLength(4, 30, new FieldLabelPair(panel.getJtxtUsername(), panel.getJlblUsernameError(), "korisnicko ime"));
        validator.validateStringLength(5, -1, new FieldLabelPair(panel.getJtxtPassword(), panel.getJlblPasswordError(), "lozinka"));
        validator.validataStringEmail(new FieldLabelPair(panel.getJtxtMail(), panel.getJlblMailError(), "e-mail"));
    }

    private List initializeFieldLabelPairs() {
        return new LinkedList() {
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
