/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Rosalijos
 */
public class RegistrantSockets {
    private String ipAddress;
    private int port;
    
    public RegistrantSockets(int port) throws UnknownHostException{
        this(InetAddress.getLocalHost().getHostAddress(), port);
    }
    
    public RegistrantSockets(String ipAddress, int port){
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public void sendSocketAdd(Registrant registrant){
        try{
          java.net.Socket socket = new java.net.Socket(ipAddress, port);
          DataOutputStream outStream =new DataOutputStream(socket.getOutputStream());

          ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
          outStream.writeInt(DatabaseCommands.ADD_REGISTRANT);
          objOutStream.writeObject(registrant);

          outStream.flush();
          outStream.close();
          objOutStream.close();
          socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }
    
    public void sendSocketUpdate(Registrant registrantOld, Registrant registrantNew){
        try{
          java.net.Socket socket=new java.net.Socket(ipAddress, port);
          
          DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
          ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());

          outStream.writeInt(DatabaseCommands.UPDATE_REGISTRANT);
          
          ArrayList<Registrant> registrants = new ArrayList<>();
          registrants.add(registrantOld);
          registrants.add(registrantNew);
          
          objOutStream.writeObject(registrants);

          outStream.flush();
          outStream.close();
          objOutStream.close();
          socket.close();
       }catch(Exception e){
            e.printStackTrace();
       }
        
    }
    
    public void sendSocketDelete(Registrant registrant){
        try{
          java.net.Socket socket=new java.net.Socket(ipAddress, port);
          DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
          ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());

          outStream.writeInt(DatabaseCommands.DELETE_REGISTRANT);
          objOutStream.writeObject(registrant);

          outStream.flush();
          outStream.close();
          outStream.close();
          socket.close();
       }catch(Exception e){
            e.printStackTrace();
       }
       
    }
    
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public String getIpAddress(){
        return ipAddress;
    }
    
    public int getPort(){
        return port;
    }
    
}
