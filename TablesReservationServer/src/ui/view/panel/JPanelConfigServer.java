/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.panel;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author jelen
 */
public class JPanelConfigServer extends javax.swing.JPanel {

    /**
     * Creates new form JPanelConfigServer
     */
    public JPanelConfigServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jtxtPort = new javax.swing.JTextField();
        jBtnChange = new javax.swing.JButton();
        jBtnSave = new javax.swing.JButton();

        jLabel2.setText("Port:");

        jBtnChange.setText("Izmeni");

        jBtnSave.setText("Sačuvaj");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnChange)
                        .addGap(29, 29, 29)
                        .addComponent(jBtnSave))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSave)
                    .addComponent(jBtnChange))
                .addGap(58, 58, 58))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnChange;
    private javax.swing.JButton jBtnSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jtxtPort;
    // End of variables declaration//GEN-END:variables

    public JButton getjBtnChange() {
        return jBtnChange;
    }

    public JButton getjBtnSave() {
        return jBtnSave;
    }

    public JTextField getJtxtPort() {
        return jtxtPort;
    }
}