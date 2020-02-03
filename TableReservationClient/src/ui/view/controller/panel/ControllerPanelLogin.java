/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.controller.panel;

import controller.BLController;
import exception.CommunicationException;
import exception.ValidationException;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ui.coordinator.GUICoordinator;
import ui.view.panel.JPanelLogin;
import util.ActorRole;
import util.FieldLabelPair;

/**
 *
 * @author jeca
 */
public class ControllerPanelLogin {

    private static ControllerPanelLogin instance;
    private JPanelLogin panel;
    private List<FieldLabelPair> fieldLabelPairs;

    private ControllerPanelLogin() {

    }

    public static ControllerPanelLogin getInstance() {
        if (instance == null) {
            instance = new ControllerPanelLogin();
        }
        return instance;
    }

    public JPanelLogin getPanel() {
        initializePanel();
        return panel;
    }

    private void initializePanel() {
        panel = new JPanelLogin();
        addEventHandlers();
        initializeFieldLabelPairs();
    }

    private void addEventHandlers() {
        this.panel.getJbtnLogin().addActionListener(e -> onLoginButtonClicked());
    }

    private void onLoginButtonClicked() {
        String username = panel.getJtxtUsername().getText();
        String password = String.valueOf(panel.getJtxtPassword().getPassword());

        try {
            validation(fieldLabelPairs);
            ActorRole role = BLController.getInstance().login(username, password);
            closeFrame();
            GUICoordinator.getInstance().showActorForm(role);
        } catch (ValidationException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeFrame() {
        Window w = SwingUtilities.getWindowAncestor(this.panel);
        w.dispose();
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
        this.fieldLabelPairs = new ArrayList() {
            {
                add(new FieldLabelPair(panel.getJtxtUsername(), panel.getJlblUsernameError(), "korisnicko ime"));
                add(new FieldLabelPair(panel.getJtxtPassword(), panel.getJlblPasswordError(), "lozinka"));
            }
        };
    }
}
