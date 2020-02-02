/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author student1
 */
public interface RequestOperation extends Serializable {

    public static final int LOGIN = 1;
    public static final int REGISTER = 2;
    public static final int GET_ALL_RESTAURANTS = 3;
    public static final int LOGOUT = 4;
    public static final int DEACTIVATE_USER = 5;
    public static final int SAVE_RESTAURANT = 6;
    public static final int UPDATE_RESTAURANT = 7;
    public static final int DELETE_RESTAURANT = 8;
    public static final int CREATE_RESERVATION = 9;
}
