/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;

/**
 *
 * @author jeca
 */
public class Admin {
    long id;
    Date authorizationDate;
    String permission;

    public Admin() {
    }

    public Admin(long id, Date authorizationDate, String permission) {
        this.id = id;
        this.authorizationDate = authorizationDate;
        this.permission = permission;
    }
}
