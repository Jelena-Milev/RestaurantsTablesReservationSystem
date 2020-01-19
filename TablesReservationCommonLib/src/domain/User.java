/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class User extends Actor {

    private String mail;
    private Date dateJoined;

    public User(Long id, String username, String password, String name, String lastname, String mail, Date dateJoined) {
        super(id, username, password, name, lastname);
        this.mail = mail;
        this.dateJoined = dateJoined;
    }

    public User(Long id, String mail, Date dateJoined) {
        this.id = id;
        this.mail = mail;
        this.dateJoined = dateJoined;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getTableName() {
        //da ovde bude "User u LEFT JOIN Actor a on u.id=a.id"
        return "User";
    }

    @Override
    public String getAllColumnNames() {
        return "id, mail, dateJoined";
    }

    @Override
    public String getInsertColumnNames() {
        return "id, mail, dateJoined";
    }    
    
    @Override
    public String getDefaultWhereClause() {
//        return "u.id = "+this.id;
        return "id = " + this.id;
    }

    @Override
    public String getColumnValues() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return this.id+", \""+this.mail+"\", \""+format.format(this.dateJoined)+"\"";
    }
    
    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> users = new ArrayList();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String mail = rs.getString("mail");
                Date date = new Date(rs.getDate("dateJoined").getTime());
                User user = new User(id, mail, dateJoined);
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

}
