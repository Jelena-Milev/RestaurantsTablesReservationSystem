/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.DomainObject;
import validator.impl.ValidatorLoginActor;

/**
 *
 * @author jeca
 */
public class SOLogin extends SystemOperation{

    public SOLogin(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidatorLoginActor();
    }

    
    @Override
    protected void operation() throws Exception {
        
    }
    
}
