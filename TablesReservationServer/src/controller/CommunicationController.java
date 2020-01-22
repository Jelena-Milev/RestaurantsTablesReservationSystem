/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import thread.ServerThread;

/**
 *
 * @author jeca
 */
public class CommunicationController {
    
    private static CommunicationController instance;
    private ServerThread serverThread;
    
    private CommunicationController(){}
    
    public static CommunicationController getInstance(){
        if(instance == null){
            instance = new CommunicationController();
        }
        return instance;
    }
    
    public void startServer() throws IOException{
        if(serverThread == null || serverThread.isAlive() == false){
            serverThread = new ServerThread();
            serverThread.start();
        }
    }

    public void stopServer() throws IOException {
        if(serverThread!=null && serverThread.getServerSocket().isBound()){
            serverThread.stopServerThread();
        }
    }
}
