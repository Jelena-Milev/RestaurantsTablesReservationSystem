/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import exception.ValidationException;
import java.util.List;
import util.FieldLabelPair;

/**
 *
 * @author jeca
 */
public interface Validator {
    public void validateStringEmpty(FieldLabelPair pair) throws ValidationException;
    public void validateStringsEmpty(List<FieldLabelPair> pairs) throws ValidationException;
    public void validateStringLength(int min, int max, FieldLabelPair pair) throws ValidationException;
    public void validataStringEmail(FieldLabelPair pair) throws ValidationException;
    public int validateInt(FieldLabelPair pair) throws ValidationException;    
}
