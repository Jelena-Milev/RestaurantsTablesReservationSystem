/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.object.DomainObject;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeca
 */
public class Actor extends DomainObject implements Serializable{

    protected Long id;
    protected String username;
    protected String password;
    protected String name;
    protected String lastname;
    private boolean active;

    public Actor(Long id, String username, String password, String name, String lastname, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.active = active;
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
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String getTableName() {
        return "Actor";
    }

    @Override
    public String getAllColumnNames() {
        return "id, username, password, name, lastname, active";
    }

    @Override
    public String getInsertColumnNames() {
        return "username, password, name, lastname, active";
    }
    
    @Override
    public String getSelectWhereClause() {
        return "username = \"" + this.username+"\"";
    }

    @Override
    public String getColumnValues() {
        return String.format("\"%s\", \"%s\", \"%s\", \"%s\", %b", username, password, name, lastname, active);
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
                boolean active = rs.getBoolean("active");
                Actor actor = new Actor(id, username, password, name, lastname, active);
                actors.add(actor);
            }

        } catch (SQLException ex) {
            //todo
            ex.printStackTrace();
        }
        return actors;
    }

    @Override
    public String getUpdateClause() {
        return String.format("username = \"%s\", password = \"%s\", name = \"%s\", lastname = \"%s\", active = %b", username, password, name, lastname, active);
    }

    @Override
    public String getUpdateWhereClause() {
        return "id = "+id;
    }

    @Override
    public String getDeleteClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDeleteWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSelectAllWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
