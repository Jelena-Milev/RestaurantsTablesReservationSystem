/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import database.broker.DatabaseBroker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeca
 */
public class ServerThread extends Thread {

    private final List<ClientThread> clients;
    private final ServerSocket serverSocket;

    public ServerThread() throws IOException {
        this.clients = new ArrayList();
        this.serverSocket = new ServerSocket(9000);
    }

    @Override
    public void run() {
        while (serverSocket.isClosed() == false) {
            System.out.println("Waiting for client...");
            try {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket);
                clients.add(clientThread);
                clientThread.start();
                System.out.println("Client connected");
            } catch (SocketException ex) {
                //TODO: Popraviti
                System.out.println("Soket je ugasen");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void stopServerThread() throws IOException {
        serverSocket.close();
    }

}
