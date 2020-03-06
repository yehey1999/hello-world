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
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author Rosalijos
 */
public class ServerFinder {
    private DatagramSocket c;
    private int receivedPort;

    public int getReceivedPort() {
        return receivedPort;
    }

    public InetSocketAddress findServer(){
        InetSocketAddress serverAddress=null;
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                c.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            }catch (IOException e) {
                e.printStackTrace();
            }

            // Broadcast the message over all the network interfaces
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
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
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }

            System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);
            //We have a response
            System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.contains("DISCOVER_FUIFSERVER_RESPONSE")) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                System.out.println("RECEIVED SOMETHING");
                serverAddress=new InetSocketAddress(receivePacket.getAddress(),Integer.parseInt(message.substring("DISCOVER_FUIFSERVER_RESPONSE".length())));
                //serverAddress.
            }
            //Close the port!
            c.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return serverAddress;
    }  
    
    
    public static void main(String args[]){
        ServerFinder s= new ServerFinder();
        InetSocketAddress a = s.findServer();
        
        System.out.println(a.getAddress().getHostAddress());
    }
}
