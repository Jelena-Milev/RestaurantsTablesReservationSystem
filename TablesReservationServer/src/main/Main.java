/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ui.controller.MainFormController;
import ui.view.MainForm;

/**
 *
 * @author jeca
 */
public class Main {
    
    public static void main(String[] args) {
//        MainForm mainForm = new MainForm();
//        mainForm.setVisible(true);
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        MainFormController.getInstance().showForm();
    }
}
