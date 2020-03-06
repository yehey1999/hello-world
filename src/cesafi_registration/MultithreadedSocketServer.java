/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cesafi_registration;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Rosalijos
 */
public class MultithreadedSocketServer implements Runnable {
  
  private ExecutorService pool;
  private int port = 10000;
  
  public MultithreadedSocketServer(){
  }

  public MultithreadedSocketServer(ExecutorService pool){
      this.pool = pool;
  }
  
  public void run(){
        try{
          ServerSocket server=new ServerSocket(port);
          int counter=0;
          System.out.println("Server Started ....");

          while(true){
            counter++;
            Socket serverClient=server.accept();  
                       
            System.out.println(" >> " + "Client No:" + counter + " started!");

            ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
            pool.execute(sct);
          }
        }catch(Exception e){
          System.out.println(e);
        }      
  }
  
}
