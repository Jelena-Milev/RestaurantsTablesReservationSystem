/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class Actor extends DomainObject {

    protected Long id;
    protected String username;
    protected String password;
    protected String name;
    protected String lastname;

    public Actor(Long id, String username, String password, String name, String lastname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }

    public Actor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Actor() {
    }

    public Actor(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.lastname = user.getLastname();
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    @Override
    public String getTableName() {
        return "Actor";
    }

    @Override
    public String getAllColumnNames() {
        return "id, username, password, name, lastname";
    }

    @Override
    public String getInsertColumnNames() {
        return "username, password, name, lastname";
    }
    
    @Override
    public String getDefaultWhereClause() {
        return "username = \"" + this.username+"\"";
    }

    @Override
    public String getColumnValues() {
        return String.format("\"%s\", \"%s\", \"%s\", \"%s\"", username, password, name, lastname);
    }

    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> actors = new ArrayList<>();
        try {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                Actor actor = new Actor(id, username, password, name, lastname);
                actors.add(actor);
            }

        } catch (SQLException ex) {
            //todo
            ex.printStackTrace();
        }
        return actors;
    }

    


}
