/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.Controller;
import domain.Actor;
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
import service.ServiceRestaurant;
import service.impl.ServiceRestaurantImpl;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ActorRole;
import util.Operation;
import util.ResponseStatus;

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
                ex.printStackTrace();
            }
        }
    }

    private ResponseObject handleRequest(RequestObject request) {
        switch (request.getOperation()) {
            case Operation.LOGIN:
                return login((Map) request.getData());
            case Operation.REGISTER:
                return register((Map) request.getData());
            case Operation.GET_ALL_RESTAURANTS:
                return getAllRestaurants();
        }
        return null;
    }

    private void sendResponse(ResponseObject response) throws IOException {
        this.objectOutputStream.writeObject(response);
    }

    private ResponseObject login(Map data) {
        String username = (String) data.get("username");
        String password = (String) data.get("password");
        ActorRole role = (ActorRole) data.get("role");

        ResponseObject response;
        try {
//            String actorRole = serviceUser.login(username, password, role);
            this.currentActor = Controller.getInstance().login(username, password, role);
            response = new ResponseObject(ResponseStatus.SUCCESS, "", "");
        } catch (Exception ex) {
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
}
