/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.panel;

import exception.CommunicationException;
import exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ui.controller.ControllerLogin;
import ui.coordinator.GUICoordinator;

/**
 *
 * @author jeca
 */
public class JPanelLogin extends javax.swing.JPanel {

    private final ControllerLogin controller;
    /**
     * Creates new form JPanelLogin
     */
    public JPanelLogin() {
        initComponents();
        controller = ControllerLogin.getInstance();  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtPasswordLogin = new javax.swing.JPasswordField();
        jtxtUsernameLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbtnLogin = new javax.swing.JButton();
        jlblUsernameErrorLogin = new javax.swing.JLabel();
        jlblPasswordErrorLogin = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Prijava na sistem"));

        jLabel1.setText("Korisničko ime:");

        jLabel2.setText("Lozinka:");

        jbtnLogin.setText("Prijava");
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });

        jlblUsernameErrorLogin.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jlblUsernameErrorLogin.setForeground(java.awt.Color.red);

        jlblPasswordErrorLogin.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jlblPasswordErrorLogin.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtUsernameLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(jtxtPasswordLogin)
                            .addComponent(jlblPasswordErrorLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblUsernameErrorLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtUsernameLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblUsernameErrorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPasswordErrorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnLogin)
                .addContainerGap(23, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        String username = jtxtUsernameLogin.getText();
        String password = String.valueOf(jtxtPasswordLogin.getPassword());
        List<FieldLabelPair> fieldLabelPairs = new ArrayList() {
            {
                add(new FieldLabelPair(jtxtUsernameLogin, jlblUsernameErrorLogin, "korisnicko ime"));
                add(new FieldLabelPair(jtxtPasswordLogin, jlblPasswordErrorLogin, "lozinka"));
            }
        };
        try {
//            validation(username, password);
            validation(fieldLabelPairs);
            String role = controller.login(username, password);
            GUICoordinator.getInstance().successfulLogin(role);
        } catch (ValidationException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (CommunicationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnLoginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbtnLogin;
    private javax.swing.JLabel jlblPasswordErrorLogin;
    private javax.swing.JLabel jlblUsernameErrorLogin;
    private javax.swing.JPasswordField jtxtPasswordLogin;
    private javax.swing.JTextField jtxtUsernameLogin;
    // End of variables declaration//GEN-END:variables
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

    private class FieldLabelPair {

        private JTextField field;
        private JLabel label;
        private String fieldName;

        public FieldLabelPair(JTextField field, JLabel label, String fieldName) {
            this.field = field;
            this.label = label;
            this.fieldName = fieldName;
        }

        public JTextField getField() {
            return field;
        }

        public void setField(JTextField field) {
            this.field = field;
        }

        public JLabel getLabel() {
            return label;
        }

        public void setLabel(JLabel label) {
            this.label = label;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

    }
}
