/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;

/**
 *
 * @author Rosalijos
 */
public class TCPClient {
    public String getServerAddress() throws IOException {

        DataOutputStream outStream = null;
        Socket s = null;
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        byte[] request = "Request Connection to Server 101".getBytes();
        
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
              continue; // Don't want to broadcast to the loopback interface
            }
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
               
                if (broadcast == null) {
                    continue;
                }
                // Send the broadcast package!
                try {
                    
                    System.out.println(interfaceAddress.getAddress().getHostAddress());
                    s = new Socket( interfaceAddress.getAddress().getHostAddress(), 8888);
                    
                    outStream = new DataOutputStream(s.getOutputStream());    
                    outStream.writeUTF("request: " + InetAddress.getLocalHost().getHostAddress());
                    //DatagramPacket sendPacket = new DatagramPacket(request, request.length, broadcast, 8888);
                    //socket.send(sendPacket);
                    

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
            }
            
            DataInputStream inStream = new DataInputStream(s.getInputStream());
            System.out.println(inStream.readUTF());
            
            outStream.flush();
            
            inStream.close();
            outStream.close();
            
        }
 
        //DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        //packet = new DatagramPacket(buf, buf.length);
        //socket.receive(packet);
        
        //String received = new String(packet.getData(), 0, packet.getLength());
        //System.out.println(packet.getAddress().getHostAddress());
        
        return "";
    }    
    
    public static void main(String args[]) throws IOException{
        TCPClient c = new TCPClient();
        System.out.println(c.getServerAddress());
    }
}
