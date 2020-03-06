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
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rosalijos
 */
public class BroadcastAddress {
    
    public static List<InetAddress> listAllBroadcastAddresses() throws SocketException, IOException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                
                if (broadcast == null) {
                    continue;
                }
                System.out.println(broadcast.getAddress());
                // Send the broadcast package!
                try {
                    byte[] data = new byte[1024];

                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, broadcast, 8888);
                    DatagramSocket c = new DatagramSocket();
                    c.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
            }            
            
            /*
            networkInterface.getInterfaceAddresses().stream() 
              .map(a -> a.getBroadcast())
              .filter(Objects::nonNull)
              .forEach(broadcastList::add);
            */
        }
        return broadcastList;
    }    
    
    public static void main(String args[]) throws SocketException, IOException{
        List<InetAddress> addresses = listAllBroadcastAddresses();
        
        for(InetAddress a: addresses)
            System.out.println(a.getHostAddress());
    }
    
}
