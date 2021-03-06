/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view;

import ui.controller.MainFormController;
import javax.swing.JMenuItem;

/**
 *
 * @author jeca
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
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
        jMenu1.add(jmiServerStart);

        jmiServerStop.setText("Stop");
        jMenu1.add(jmiServerStop);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Configuration");

        jmiConfigServer.setText("Server configuration");
        jMenu2.add(jmiConfigServer);

        jmiConfigDatabase.setText("Database configuration");
        jMenu2.add(jmiConfigDatabase);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


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

    public JMenuItem getJmiConfigDatabase() {
        return jmiConfigDatabase;
    }

    public JMenuItem getJmiConfigServer() {
        return jmiConfigServer;
    }

    public JMenuItem getJmiServerStart() {
        return jmiServerStart;
    }

    public JMenuItem getJmiServerStop() {
        return jmiServerStop;
    }   
}
