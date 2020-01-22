/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import domain.Actor;
import domain.DomainObject;
import domain.User;
import javax.xml.bind.ValidationException;
import validator.impl.ValidatorLoginActor;
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
            Actor actor = new Actor(null, user.getUsername(), user.getPassword(), user.getName(), user.getLastname());
            Long id = dbBroker.insert(actor);
            user.setId(id);
            dbBroker.insert(user);
        } catch (MySQLIntegrityConstraintViolationException e) {
//            e.printStackTrace();
            throw new Exception("Korisnik sa unetim korisnickim imenom vec postoji");
        }
    }

}
