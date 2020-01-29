/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view;

import exception.CommunicationException;
import ui.view.panel.JPanelRestaurantNew;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.controller.ControllerMenuAccount;
import ui.coordinator.GUICoordinator;

/**
 *
 * @author jeca
 */
public class JFrameAdmin extends javax.swing.JFrame {

    /**
     * Creates new form JFrameRestaurant
     */
    public JFrameAdmin() {
        initComponents();
        prepareForm();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar = new javax.swing.JMenuBar();
        jMenuRestaurant = new javax.swing.JMenu();
        jmiNewRestaurant = new javax.swing.JMenuItem();
        jmiSearchRestaurants = new javax.swing.JMenuItem();
        jMenuAccount = new javax.swing.JMenu();
        jMenuItemLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuRestaurant.setText("Restoran");

        jmiNewRestaurant.setText("Novi restoran");
        jmiNewRestaurant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNewRestaurantActionPerformed(evt);
            }
        });
        jMenuRestaurant.add(jmiNewRestaurant);

        jmiSearchRestaurants.setText("Pretraga restorana");
        jMenuRestaurant.add(jmiSearchRestaurants);

        jMenuBar.add(jMenuRestaurant);

        jMenuAccount.setText("Nalog");

        jMenuItemLogout.setText("Odjava");
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenuAccount.add(jMenuItemLogout);

        jMenuBar.add(jMenuAccount);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiNewRestaurantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNewRestaurantActionPerformed
        JPanel newRestaurantPanel = new JPanelRestaurantNew();
        this.getContentPane().removeAll();
        this.getContentPane().add(newRestaurantPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_jmiNewRestaurantActionPerformed

    private void jMenuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogoutActionPerformed
        int answer = JOptionPane.showConfirmDialog(this, "Odajava?", "Odjavljivanje", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                ControllerMenuAccount.getInstance().logout();
                GUICoordinator.getInstance().logout(this);
            } catch (CommunicationException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jMenuItemLogoutActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenuAccount;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenu jMenuRestaurant;
    private javax.swing.JMenuItem jmiNewRestaurant;
    private javax.swing.JMenuItem jmiSearchRestaurants;
    // End of variables declaration//GEN-END:variables

    private void prepareForm() {
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);

    }
}
