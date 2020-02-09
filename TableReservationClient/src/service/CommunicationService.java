/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import exception.CommunicationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ActorRole;
import util.ResponseStatus;
import util.RequestOperation;

/**
 *
 * @author jeca
 */
public class CommunicationService {

    private static CommunicationService instance;

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    
    private String serverAdress;
    private int serverPort;

    private CommunicationService() throws IOException {
        //izmesti povezivanje odavde
        loadServerParameters();
        socket = new Socket(serverAdress, serverPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public static CommunicationService getInstance() throws IOException {
        if (instance == null) {
            instance = new CommunicationService();
        }
        return instance;
    }

    public ActorRole login(Map<String, Object> data) throws CommunicationException {
        ActorRole role;
        RequestObject request = new RequestObject(RequestOperation.LOGIN, data);
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            ResponseStatus status = response.getStatus();
            if (status == ResponseStatus.ERROR) {
                throw new CommunicationException(response.getErrorMessage());
            }
            role = (ActorRole) response.getData();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        } 
        return role;
    }

    public void register(Map<String, String> data) throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.REGISTER, data);
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

    public List<Restaurant> getAllRestaurants() throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.GET_ALL_RESTAURANTS, "");
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
            RequestObject request = new RequestObject(RequestOperation.LOGOUT, "");
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
            RequestObject request = new RequestObject(RequestOperation.DEACTIVATE_USER, "");
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
        RequestObject request = new RequestObject(RequestOperation.SAVE_RESTAURANT, restaurant);
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

//    public void createReservation(DiningTable table, Date date, LocalTime timeFrom, LocalTime timeTo) throws CommunicationException {
//        Reservation reservation = new Reservation(table, null, date, timeFrom, timeTo, false);
//        RequestObject request = new RequestObject(RequestOperation.CREATE_RESERVATION, reservation);
//        try {
//            objectOutputStream.writeObject(request);
//            ResponseObject response = (ResponseObject) objectInputStream.readObject();
//            if(response.getStatus() == ResponseStatus.ERROR){
//                throw new CommunicationException(response.getErrorMessage());
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            throw new CommunicationException("Greska prilikom slanja zahteva");
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            throw new CommunicationException("Greska prilikom prijema odgovora");
//        }
//    }

    public void updateRestaurant(Restaurant restaurant) throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.UPDATE_RESTAURANT, restaurant);
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

     public List<DiningTable> findFreeTables(Map<String, Object> data) throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.GET_FREE_TABLES, data);
        List<DiningTable> tables;
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
            tables = (List<DiningTable>)response.getData();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
        return tables;
    }

    public void saveReservation(Reservation reservation) throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.SAVE_RESERVATION, reservation);
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

    public List<Reservation> getAllReservations() throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.GET_ALL_RESERVATIONS, "");
        List<Reservation> reservations;
        try {
            objectOutputStream.writeObject(request);
            ResponseObject response = (ResponseObject) objectInputStream.readObject();
            if(response.getStatus() == ResponseStatus.ERROR){
                throw new CommunicationException(response.getErrorMessage());
            }
            reservations = (List<Reservation>)response.getData();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom slanja zahteva");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new CommunicationException("Greska prilikom prijema odgovora");
        }
        return reservations;
    }

    public void cancelReservation(Reservation reservation) throws CommunicationException {
        RequestObject request = new RequestObject(RequestOperation.CANCEL_RESERVATION, reservation);
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

    private void loadServerParameters() {
        try {
            Properties properties = new Properties();
            String propertiesFileName = "config/server.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFileName);

            properties.load(fileInputStream);

            this.serverAdress = properties.getProperty("adress");
            this.serverPort = Integer.parseInt(properties.getProperty("port"));

            fileInputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
