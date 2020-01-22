/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import logic.SOLoginAdmin;
import domain.Actor;
import domain.Admin;
import domain.User;
import logic.SOLoginUser;
import logic.SORegisterUser;
import logic.SystemOperation;
import util.ActorRole;

/**
 *
 * @author jeca
 */
public class Controller {

    private static Controller instance;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

//    public void login(Actor actor) throws Exception{
//       
//    }
    public Actor login(String username, String password, ActorRole role) throws Exception {
//        Actor actor = role.equals(ActorRole.USER) ? new User(username, password) : new Admin(username, password);
        Actor actor = new Actor(username, password);
        SystemOperation so;
        if (role.equals(ActorRole.USER)) {
            so = new SOLoginUser(actor);

        } else {
            so = new SOLoginAdmin(actor);
        }
        so.execute();
        return (Actor) so.getDomainObject();
    }

    public void register(User user) throws Exception {
        SystemOperation so = new SORegisterUser(user);
        so.execute();
    }

}
