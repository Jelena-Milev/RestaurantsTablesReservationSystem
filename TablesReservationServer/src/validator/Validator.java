/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import javax.xml.bind.ValidationException;

/**
 *
 * @author jeca
 */
public interface Validator {
    public void validate(Object value) throws ValidationException;
}
