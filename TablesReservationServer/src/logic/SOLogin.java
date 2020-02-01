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
public class SOLogin extends SystemOperation {

    public SOLogin(DomainObject odo) {
        super();
        this.odo = odo;
        this.validator = new ValidatorLoginActor(this.odo);
    }

    @Override
    protected void operation() throws Exception {
        List<DomainObject> actors = dbBroker.get(odo);
        Actor actor = (Actor) actors.get(0);
        if (((Actor) odo).getPassword().equals(actor.getPassword()) == false) {
            throw new Exception("Pogresna lozinka.");
        }
        List<DomainObject> users = dbBroker.get(new User(actor.getId()));
        if (users.isEmpty() == false) {
            User user = (User) users.get(0);
            user.setUsername(actor.getUsername());
            user.setPassword(actor.getPassword());
            user.setName(actor.getName());
            user.setLastname(actor.getLastname());
            user.setActive(actor.isActive());
            odo = user;
        } else {
            List<DomainObject> admins = dbBroker.get(new Admin(actor.getId()));
            Admin admin = (Admin) admins.get(0);
            admin.setUsername(actor.getUsername());
            admin.setPassword(actor.getPassword());
            admin.setName(actor.getName());
            admin.setLastname(actor.getLastname());
            odo = admin;
        }
    }
    
    public DomainObject getDomainObject(){
        return this.odo;
    }
}
