/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import logic.systemOperation.SystemOperation;
import domain.Actor;
import domain.object.DomainObject;
import domain.User;
import validator.impl.ValidateDeactivateUser;

/**
 *
 * @author jeca
 */
public class SODeactivateUser extends SystemOperation {

    public SODeactivateUser(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidateDeactivateUser();
    }

    @Override
    protected void operation() throws Exception {
//        Actor actor = (Actor)odo;
        User user = (User) odo;
        Actor actor = new Actor(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getLastname(), false);
        int rowsUpdated = dbBroker.update(actor);
        if (rowsUpdated != 1) {
            throw new Exception("Neuspesno deaktiviranje naloga");
        }
    }
}
