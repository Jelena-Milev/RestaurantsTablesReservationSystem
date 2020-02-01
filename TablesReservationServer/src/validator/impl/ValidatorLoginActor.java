/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import domain.DomainObject;
import javax.xml.bind.ValidationException;
import validator.Validator;

/**
 *
 * @author jeca
 */
public class ValidatorLoginActor implements Validator{

    private DomainObject odo; 
    
    public ValidatorLoginActor(DomainObject odo) {
        this.odo = odo;
    }
    
    @Override
    public void validate(Object value) throws ValidationException {
        
    }
    
}
