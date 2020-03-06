/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Rosalijos
 */
public class ServerClientThread extends Thread{
    Socket serverClient;
    int clientNo;
    int squre;
    MySql database;
    
    public ServerClientThread(Socket inSocket,int counter){
      serverClient = inSocket;
      clientNo=counter;
      database = new MySql();
    }
  
    public void run(){ 
      try{        
        DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
        ObjectInputStream objInputStream = new ObjectInputStream(serverClient.getInputStream());
         
        switch(inStream.readInt()){
            case DatabaseCommands.ADD_REGISTRANT:
                Registrant regAdd = (Registrant)objInputStream.readObject();
                database.addRegistrant(regAdd);
                break;
            case DatabaseCommands.DELETE_REGISTRANT:
                Registrant regDel= (Registrant)objInputStream.readObject();
                database.deleteRegistrant(regDel);
                break;
            case DatabaseCommands.UPDATE_REGISTRANT:
                ArrayList<Registrant> regs = (ArrayList<Registrant>)objInputStream.readObject();       
                database.updateRegistrant(regs.get(0), regs.get(1));
                break;
        }
        
        inStream.close();
        objInputStream.close();
        serverClient.close();
      }catch(Exception ex){
        System.out.println(ex);
      }finally{
        System.out.println("Client -" + clientNo + " exit!! ");
      }
    }    
    
}
