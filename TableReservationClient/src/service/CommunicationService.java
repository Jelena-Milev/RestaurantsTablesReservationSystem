/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Restaurant;
import exception.CommunicationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author jeca
 */
public class CommunicationService {

    private static CommunicationService instance;

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    private CommunicationService() throws IOException {
        //izmesti povezivanje odavde
        socket = new Socket("localhost", 9000);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public static CommunicationService getInstance() throws IOException {
        if (instance == null) {
            instance = new CommunicationService();
        }
        return instance;
    }

    public void login(Map<String, Object> data) throws CommunicationException {
        RequestObject request = new RequestObject(Operation.LOGIN, data);
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            ResponseStatus status = response.getStatus();
            if (status == ResponseStatus.ERROR) {
                throw new CommunicationException(response.getErrorMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        } 
    }

    public void register(Map<String, String> data) throws CommunicationException {
        RequestObject request = new RequestObject(Operation.REGISTER, data);
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
    }

    public List<Restaurant> getRestaurants() throws CommunicationException {
        RequestObject request = new RequestObject(Operation.GET_ALL_RESTAURANTS, "");
        List<Restaurant> restaurants;
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
            restaurants = (List<Restaurant>)response.getData();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
        return restaurants;
    }

    public void logout() throws CommunicationException {
        try {
            RequestObject request = new RequestObject(Operation.LOGOUT, "");
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
    }

    public void deactivateAccount() throws CommunicationException {
        try {
            RequestObject request = new RequestObject(Operation.DEACTIVATE_USER, "");
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
    }

    public void saveRestaurant(Restaurant restaurant) throws CommunicationException {
        RequestObject request = new RequestObject(Operation.SAVE_RESTAURANT, restaurant);
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
    }

}
