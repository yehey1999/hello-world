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
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author Rosalijos
 */
public class UDPClient {
    
    private DatagramSocket socket;
    private DatagramPacket packet;
    
    public UDPClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
    }
 
    public String getServerAddress() throws IOException {
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        byte[] request = "Request Connection to Server 101".getBytes();
        
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
              continue;
            }
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null) {
                    continue;
                }
                try {
                    System.out.println(broadcast.getHostAddress());
                    DatagramPacket sendPacket = new DatagramPacket(request, request.length, broadcast, 8888);
                    socket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
 
        packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);
        
        return packet.getAddress().getHostAddress();
    }
    
    public String getImgNetworkPath(){
        System.out.println(packet.toString());
        return packet.toString();
    }
 
    public void close() {
        socket.close();
    }

    public static void main(String args[]) throws SocketException, UnknownHostException, IOException{
        UDPClient client = new UDPClient();
        System.out.println("Host Address: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Server Address: " + client.getServerAddress());
        client.close();
    }
}
