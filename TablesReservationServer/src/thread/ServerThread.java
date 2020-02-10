/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import database.broker.DatabaseBroker;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author jeca
 */
public class ServerThread extends Thread {

    private final List<ClientThread> clients;
    private final ServerSocket serverSocket;
    private int port;

    public ServerThread() throws IOException {
        loadPort();
        this.clients = new ArrayList();
        this.serverSocket = new ServerSocket(port);
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
        stopClientHandlers();
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public void stopServerThread() throws IOException {
        serverSocket.close();
    }

    private void loadPort() {
        try {
            Properties properties = new Properties();
            String propertiesFileName = "config/server.properties";
            FileInputStream fileInputStream = new FileInputStream(propertiesFileName);

            properties.load(fileInputStream);

            this.port = Integer.parseInt(properties.getProperty("socket_port"));

            fileInputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void stopClientHandlers() {
        for (ClientThread client : clients) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
