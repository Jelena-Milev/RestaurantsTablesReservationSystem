/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.systemOperation.impl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import logic.systemOperation.SystemOperation;
import domain.Actor;
import domain.object.DomainObject;
import domain.User;
import validator.impl.ValidatorRegisterUser;

/**
 *
 * @author jeca
 */
public class SORegisterUser extends SystemOperation {

    public SORegisterUser(DomainObject odo) {
        super();
        this.odo = odo;
        validator = new ValidatorRegisterUser();
    }

    @Override
    protected void operation() throws Exception {
        try {
            User user = (User) odo;
            Actor actor = new Actor(null, user.getUsername(), user.getPassword(), user.getName(), user.getLastname(), user.isActive());
            Long id = dbBroker.insert(actor);
            user.setId(id);
            dbBroker.insert(user);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            throw new Exception("Korisnik sa unetim korisnickim imenom vec postoji");
        }
    }

}
