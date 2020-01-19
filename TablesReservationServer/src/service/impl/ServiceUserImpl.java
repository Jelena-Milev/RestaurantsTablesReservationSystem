/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.impl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import database.broker.DatabaseBroker;
import domain.DomainObject;
import domain.Actor;
import domain.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.ValidationException;
import service.ServiceUser;

/**
 *
 * @author jeca
 */
public class ServiceUserImpl implements ServiceUser {

    private DatabaseBroker dbBroker;

    public ServiceUserImpl() {
        this.dbBroker = DatabaseBroker.getInstance();
    }

    @Override
    public String login(String username, String password) throws Exception {
        List<DomainObject> actors = getActorsByUsername(username, password);
        if (actors == null || actors.isEmpty()) {
            throw new Exception("Ne postoji korisnik sa unetim korisnickim imenom");
        }
        Actor actor = (Actor) actors.get(0);
        if (actor.getPassword().equals(password.trim()) == false) {
            throw new Exception("Lozinka nije ispravna");
        }
        return getActorRole(actor);
    }

    private List<DomainObject> getActorsByUsername(String username, String password) throws Exception {
        DomainObject actor = new Actor(username, password);
        try {
            List<DomainObject> result = dbBroker.get(actor);
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska u konekciji sa bazom");
        } 
    }

    private String getActorRole(Actor actor) {
        try {
            User user = new User(actor.getId(), actor.getUsername(), actor.getPassword(), actor.getName(), actor.getLastname(), null, null);
            List<DomainObject> users = dbBroker.get(user);
            if(users.isEmpty() == false){
                user.setMail(((User)users.get(0)).getMail());
                user.setDateJoined(((User)users.get(0)).getDateJoined());
                return "User";
            }return "Admin";
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public void register(User user) throws Exception {
        try {
            Actor actor = new Actor(user);
            Long id = dbBroker.insert(actor);
            user.setId(id);
            dbBroker.insert(user);
            dbBroker.commit();
        } catch(MySQLIntegrityConstraintViolationException ex){
            dbBroker.rollback();
            throw new ValidationException("Korisnik sa unetim korisnickim imenom vec postoji");
        } catch (SQLException ex) {
//            ex.printStackTrace();
            try {
                dbBroker.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        }
    }

}
