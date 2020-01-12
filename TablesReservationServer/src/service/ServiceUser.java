package service;


import domain.Actor;
import domain.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeca
 */
public interface ServiceUser {
    public String login(String username, String password) throws Exception;

    public void register(User user) throws Exception;
}
