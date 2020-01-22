/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.coordinator;

import javax.swing.JOptionPane;
import ui.view.JFrameMain;
import ui.view.JFrameAdmin;
import ui.view.JFrameUser;
import util.ActorRole;

/**
 *
 * @author jeca
 */
public class GUICoordinator {

    private static GUICoordinator instance;
    private JFrameMain firstForm;

    private GUICoordinator() {
    }

    public static GUICoordinator getInstance() {
        if (instance == null) {
            instance = new GUICoordinator();
        }
        return instance;
    }

//    public JFrameMain getFirstForm() {
//        return firstForm;
//    }

    public void setFirstForm(JFrameMain firstForm) {
        this.firstForm = firstForm;
    }

    public void successfulLogin(ActorRole role) {
        this.firstForm.dispose();
        if (role.equals(ActorRole.ADMIN)) {
            new JFrameAdmin().setVisible(true);
        } else {
            new JFrameUser().setVisible(true);
        }
    }

    public void successfulRegistration() {
        JOptionPane.showMessageDialog(null, "Uspesna registracija, mozete se prijaviti", "Registracija", JOptionPane.INFORMATION_MESSAGE);
        this.firstForm.switchToLogin();
    }
}
