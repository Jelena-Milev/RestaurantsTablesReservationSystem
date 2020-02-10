/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.Controller;
import domain.Actor;
import domain.Admin;
import domain.DiningTable;
import domain.Reservation;
import domain.Restaurant;
import domain.User;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ActorRole;
import util.ResponseStatus;
import util.RequestOperation;

/**
 *
 * @author jeca
 */
public class ClientThread extends Thread {

    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private Actor currentActor;

    ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (socket.isClosed() == false) {
            try {
                RequestObject request = (RequestObject) objectInputStream.readObject();
                ResponseObject response = handleRequest(request);
                sendResponse(response);
            } catch (EOFException ex) {
                try {
                    socket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
                break;
            } catch (IOException | ClassNotFoundException ex) {
                try {
                    //klijent kad se sam iskljuci treba da se zatvori socket i ugasice se nit
                    socket.close();
                } catch (IOException ex1) {
                    ex.printStackTrace();
                }
                System.out.println("Klijent se iskljucio, nit ugasena");
            }
        }
    }

    private ResponseObject handleRequest(RequestObject request) {
        switch (request.getOperation()) {
            case RequestOperation.LOGIN:
                return login((Map) request.getData());
            case RequestOperation.REGISTER:
                return register((Map) request.getData());
            case RequestOperation.GET_ALL_RESTAURANTS:
                return getAllRestaurants();
            case RequestOperation.LOGOUT:
                return logout();
            case RequestOperation.DEACTIVATE_USER:
                return deactivateUser();
            case RequestOperation.SAVE_RESTAURANT:
                return saveRestaurant((Restaurant) request.getData());
            case RequestOperation.UPDATE_RESTAURANT:
                return updateRestaurant((Restaurant) request.getData());
            case RequestOperation.SAVE_RESERVATION:
                return saveReservation((Reservation) request.getData());
            case RequestOperation.GET_FREE_TABLES:
                return getFreeTables((Map) request.getData());
            case RequestOperation.GET_ALL_RESERVATIONS:
                return getAllReservations();
            case RequestOperation.CANCEL_RESERVATION:
                return cancelReservation((Reservation) request.getData());
            case RequestOperation.DEACTIVATE_RESTAURANT:
                return deactivateRestaurant((Restaurant) request.getData());
        }
        return null;
    }

    private void sendResponse(ResponseObject response) throws IOException {
        this.objectOutputStream.writeObject(response);
    }

    private ResponseObject login(Map data) {
//        System.out.println("\nBefore login");
//        showCurrentActor();
        String username = (String) data.get("username");
        String password = (String) data.get("password");

        ActorRole role;
        ResponseObject response;
        try {
            this.currentActor = Controller.getInstance().login(username, password);
            role = this.currentActor instanceof User ? ActorRole.USER : ActorRole.ADMIN;
            response = new ResponseObject(ResponseStatus.SUCCESS, role, "");
//            System.out.println("\nAfter login");
//            showCurrentActor();
        } catch (Exception ex) {
//            System.out.println("\nUnsuccessful login");
//            showCurrentActor();
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject register(Map data) {
        User user = new User(null, (String) data.get("username"), (String) data.get("password"), (String) data.get("name"), (String) data.get("lastname"), (String) data.get("mail"), new Date(), true);

        ResponseObject response;
        try {
            Controller.getInstance().register(user);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject getAllRestaurants() {
        ResponseObject response;
        try {
            List<Restaurant> restaurants = Controller.getInstance().getAllRestaurants();
            response = new ResponseObject(ResponseStatus.SUCCESS, restaurants, "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject logout() {
//        System.out.println("\nBefore logout");
//        showCurrentActor();
        this.currentActor = null;
//        System.out.println("\nAfter logout");
//        showCurrentActor();
        return new ResponseObject(ResponseStatus.SUCCESS, "", "");
    }

    private void showCurrentActor() {
//        if (currentActor != null) {
//            System.out.println("User? " + (currentActor instanceof User));
//            System.out.println("Admin? " + (currentActor instanceof Admin));
//            System.out.println("Username:" + currentActor.getUsername());
//        } else {
//            System.out.println("Niko nije prijavljen, currentActor: " + currentActor);
//        }
    }

    private ResponseObject deactivateUser() {
        try {
            Controller.getInstance().deactivateUser(currentActor);
            logout();
            return new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            return new ResponseObject(ResponseStatus.ERROR, "", "Greska pri deaktivaciji naloga: " + ex.getMessage());
        }
    }

    private ResponseObject saveRestaurant(Restaurant restaurant) {
        ResponseObject response;
        restaurant.setAdmin((Admin) currentActor);
        try {
            Controller.getInstance().saveRestaurant(restaurant);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject saveReservation(Reservation reservation) {
        ResponseObject response;
        reservation.setUser((User) currentActor);
        try {
            Controller.getInstance().saveReservation(reservation);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject updateRestaurant(Restaurant restaurant) {
        ResponseObject response;
        restaurant.setAdmin((Admin) currentActor);
        try {
            Controller.getInstance().updateRestaurant(restaurant);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject getFreeTables(Map data) {
        ResponseObject response;
        try {
            List<DiningTable> tables = Controller.getInstance().getFreeTables(data);
            response = new ResponseObject(ResponseStatus.SUCCESS, tables, "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject getAllReservations() {
        ResponseObject response;
        try {
            List<Reservation> reservations = Controller.getInstance().getUsersReservations((User) currentActor);
            response = new ResponseObject(ResponseStatus.SUCCESS, reservations, "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject cancelReservation(Reservation reservation) {
        ResponseObject response;
        //reservation.setUser((User) currentActor);
        try {
            Controller.getInstance().cancelReservation(reservation);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    private ResponseObject deactivateRestaurant(Restaurant restaurant) {
        ResponseObject response;
        try {
            Controller.getInstance().deactivateRestaurant(restaurant);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
            response = new ResponseObject(ResponseStatus.ERROR, "", ex.getMessage());
        }
        return response;
    }

    public Socket getSocket() {
         return socket;
    }

}
