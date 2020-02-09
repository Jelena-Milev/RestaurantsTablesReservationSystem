/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import exception.ValidationException;
import java.util.List;
import util.FieldLabelPair;
import validator.Validator;

/**
 *
 * @author jeca
 */
public class ValidatorImplementation implements Validator {
    
    @Override
    public void validateStringEmpty(FieldLabelPair pair) throws ValidationException {
        pair.getLabel().setText("");

        if (pair.getField().getText().isEmpty()) {
            pair.getLabel().setText("Morate uneti " + pair.getFieldName());
            throw new ValidationException(pair.getFieldName() + " je prazan");
        }
    }

    @Override
    public void validateStringLength(int min, int max, FieldLabelPair pair) throws ValidationException {
        pair.getLabel().setText("");

        validateStringEmpty(pair);
        String text = pair.getField().getText();
        if (min > 0 && text.length() < min) {
            pair.getLabel().setText(pair.getFieldName() + " mora imati minimalno " + min + " karaktera.");
            throw new ValidationException(pair.getFieldName() + "mora imati minimalno " + min + " karaktera.");
        }
        if (max > 0 && max > min && text.length() > max) {
            pair.getLabel().setText(pair.getFieldName() + " mora imati maksimalno " + max + " karaktera.");
            throw new ValidationException(pair.getFieldName() + " mora imati maksimalno " + max + " karaktera.");
        }
    }

    @Override
    public void validataStringEmail(FieldLabelPair pair) throws ValidationException {
        pair.getLabel().setText("");

        validateStringEmpty(pair);
        String mail = pair.getField().getText();
        if (mail.contains("@") == false) {
            pair.getLabel().setText("Email mora sadrzati karakter @");
            throw new ValidationException("Email ne sadrzi @");
        }
        if(mail.indexOf("@") == mail.length()-1){
            pair.getLabel().setText("Email nije validan");
            throw new ValidationException("Email nije validan");
        }
    }

    @Override
    public int validateInt(FieldLabelPair pair) throws ValidationException {
        pair.getLabel().setText("");
        
        try {
            int numberOfPeople = Integer.parseInt(pair.getField().getText().trim());
            return numberOfPeople;
        } catch (NumberFormatException ex) {
            pair.getLabel().setText("Morate uneti cifru");
            throw new ValidationException("Nije unet int");
        }
    }

    @Override
    public void validateStringsEmpty(List<FieldLabelPair> pairs) throws ValidationException {
        for (FieldLabelPair fieldLabelPair : pairs) {
            fieldLabelPair.getLabel().setText("");
            if (fieldLabelPair.getField().getText().isEmpty()) {
                fieldLabelPair.getLabel().setText("Morate uneti " + fieldLabelPair.getFieldName());
            }
        }
        if (pairs.stream().anyMatch(pair -> pair.getField().getText().isEmpty())) {
            throw new ValidationException("Polje ne sme biti prazno");
        }
    }
}
