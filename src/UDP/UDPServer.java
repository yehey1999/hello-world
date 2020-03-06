/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 *
 * @author Rosalijos
 */
public class UDPServer implements Runnable {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[1024];
    private String imgNetworkPath;
    
    public UDPServer(String imgNetworkPath) throws SocketException {
        socket = new DatagramSocket(8888);
        this.imgNetworkPath = imgNetworkPath;
    }
 
    @Override
    public void run(){
        running = true;
        
        System.out.println("Server ready....");
        
        try{
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress(); 

                if (new String(packet.getData(), 0, packet.getLength()).trim().equals("Request Connection to Server 101")) {
                    DatagramPacket replyPacket = new DatagramPacket(imgNetworkPath.getBytes(), imgNetworkPath.getBytes().length, address, packet.getPort());
                    socket.send(replyPacket);
                    running = false;
                    continue;
                }
                
                socket.send(packet);
            }
        }catch(Exception e){
             e.printStackTrace();
        }
        socket.close();
    }    
    
    public static void main(String args[]) throws SocketException{
        //new UDPServer().start();
    }
}
