/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import cesafi_registration.ServerClientThread;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rosalijos
 */
public class TCPServer {
  public void startServer(){
        try{       
          ServerSocket server=new ServerSocket(8888);
          DataInputStream in;
          DataOutputStream out = null;
          
          int counter=0;
          System.out.println("Server Started ....");

          while(true){
            counter++;
            Socket serverClient=server.accept();  //server accept the client connection request
            System.out.println(" >> " + "Client No:" + counter + " started!");
            
            in = new DataInputStream(serverClient.getInputStream());
            
            System.out.print(in.readUTF());
            
            out = new DataOutputStream(serverClient.getOutputStream());
            out.writeUTF("Server Address: " + InetAddress.getLocalHost().getHostAddress());
            
            out.flush();
            out.close();
          }
          

        }catch(Exception e){
          System.out.println(e);
        }      
  }
  
  public static void main(String args[]){
      new TCPServer().startServer();
  }
}
