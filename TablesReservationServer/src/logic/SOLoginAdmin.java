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
import logic.SystemOperation;
import validator.impl.ValidatorLoginActor;

/**
 *
 * @author jeca
 */
public class SOLoginAdmin extends SystemOperation {

    public SOLoginAdmin(Actor actor) {
        super();
        odo = actor;
        validator = new ValidatorLoginActor();
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
        List<DomainObject> admins = dbBroker.get(new Admin(actor.getId()));
        if (admins.isEmpty() == true) {
            throw new Exception("Ne postoji admin sa unetim korisnickim imenom");
        }
        Admin admin = (Admin) admins.get(0);
        admin.setUsername(actor.getUsername());
        admin.setPassword(actor.getPassword());
        admin.setName(actor.getName());
        admin.setLastname(actor.getLastname());
        odo = admin;
    }
}
