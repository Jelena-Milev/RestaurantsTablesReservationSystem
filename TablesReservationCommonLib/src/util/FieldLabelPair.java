/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author jeca
 */
public class FieldLabelPair {

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
