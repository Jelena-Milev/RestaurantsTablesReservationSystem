/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Actor;
import domain.Admin;
import domain.DomainObject;
import domain.User;
import java.util.List;
import validator.impl.ValidatorLoginActor;

/**
 *
 * @author jeca
 */
public class SOLoginUser extends SystemOperation {

    public SOLoginUser(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidatorLoginActor();
    }

    @Override
    protected void operation() throws Exception {
        List<DomainObject> actors = dbBroker.get(odo);
        if (actors == null || actors.isEmpty()) {
            throw new Exception("Korisnicko ime ne postoji.");
        }
        Actor actor = (Actor) actors.get(0);
        if (((Actor) odo).getPassword().equals(actor.getPassword()) == false) {
            throw new Exception("Pogresna lozinka.");
        }
        List<DomainObject> users = dbBroker.get(new User(actor.getId()));
        if (users.isEmpty() == true) {
            throw new Exception("Ne postoji korisnik sa unetim korisnickim imenom");
        }
        User user = (User) users.get(0);
        user.setUsername(actor.getUsername());
        user.setPassword(actor.getPassword());
        user.setName(actor.getName());
        user.setLastname(actor.getLastname());
        odo = user;
    }

}
