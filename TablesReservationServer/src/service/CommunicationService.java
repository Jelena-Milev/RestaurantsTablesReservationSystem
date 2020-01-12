/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import thread.ServerThread;

/**
 *
 * @author jeca
 */
public class CommunicationService {
    
    private static CommunicationService instance;
    private ServerThread serverThread;
    
    private CommunicationService(){}
    
    public static CommunicationService getInstance(){
        if(instance == null){
            instance = new CommunicationService();
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
