/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view;

import ui.controller.MainFormController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jeca
 */
public class MainForm extends javax.swing.JFrame {

    private final MainFormController controller;
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        controller = MainFormController.getInstance();
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiServerStart = new javax.swing.JMenuItem();
        jmiServerStop = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiConfigServer = new javax.swing.JMenuItem();
        jmiConfigDatabase = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Server");

        jmiServerStart.setText("Start");
        jmiServerStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiServerStartActionPerformed(evt);
            }
        });
        jMenu1.add(jmiServerStart);

        jmiServerStop.setText("Stop");
        jmiServerStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiServerStopActionPerformed(evt);
            }
        });
        jMenu1.add(jmiServerStop);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Configuration");

        jmiConfigServer.setText("Server configuration");
        jMenu2.add(jmiConfigServer);

        jmiConfigDatabase.setText("Database configuration");
        jMenu2.add(jmiConfigDatabase);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 283, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiServerStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiServerStartActionPerformed
        try {
            this.controller.startServer();
//            jmiServerStart.setEnabled(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Server nije pokrenut", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jmiServerStartActionPerformed

    private void jmiServerStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiServerStopActionPerformed
        try {
            this.controller.stopServer();
//            jmiServerStart.setEnabled(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Server nije zaustavljen", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jmiServerStopActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jmiConfigDatabase;
    private javax.swing.JMenuItem jmiConfigServer;
    private javax.swing.JMenuItem jmiServerStart;
    private javax.swing.JMenuItem jmiServerStop;
    // End of variables declaration//GEN-END:variables

    
}
