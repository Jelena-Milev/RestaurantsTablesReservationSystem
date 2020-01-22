/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeca
 */
public class Admin extends Actor{

    private Date authorizationDate;
    private String permission;

    public Admin() {
    }

    public Admin(Date authorizationDate, String permission) {
        this.authorizationDate = authorizationDate;
        this.permission = permission;
    }
    
    public Admin(Long id, Date authorizationDate, String permission) {
        this.id = id;
        this.authorizationDate = authorizationDate;
        this.permission = permission;
    }

    public Admin(Long id) {
        this.id=id;
    }

    public Date getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(Date authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getTableName() {
        return "Admin";
    }

    @Override
    public String getAllColumnNames() {
        return "id, authorizationDate, permission";
    }

    @Override
    public String getDefaultWhereClause() {
//        return "u.id = "+this.id;
        return "id = " + this.id;
    }
    
    @Override
    public List<DomainObject> getObjectsFromResultSet(ResultSet rs) {
        List<DomainObject> admins = new ArrayList();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String permission = rs.getString("permission");
                Date authorizationDate = new Date(rs.getDate("authorizationDate").getTime());
                Admin admin = new Admin(id, authorizationDate, permission);
                admins.add(admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return admins;
    }
    
    
}
