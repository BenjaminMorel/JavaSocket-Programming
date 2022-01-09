package Serveur;

import Model.ClientModel;
import View.ClientChoice;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server {

    public static ArrayList<ClientModel> connectedClients = new ArrayList<>();
    public static void main(String[] args) {

//        Socket srvSocket = null ;
        ServerSocket mySkServer;
        InetAddress localAddress = null;
//        PrintWriter pout;
        String interfaceName = "lo";
        int ClientNo = 1;
        int IdRefreshPage = 0;
        String rootDirectory = "";
        ArrayList<String> path = new ArrayList<>();


        try {

            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();

                if (!ia.isLinkLocalAddress()) {
                    if (!ia.isLoopbackAddress()) {
                        System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
                        localAddress = ia;
                    }
                }
            }

            mySkServer = new ServerSocket(45000, 10, localAddress);
//            System.out.println("Default Timeout: " + mySkServer.getSoTimeout());
//            System.out.println("Used IpAddress: " + mySkServer.getInetAddress());
            System.out.println("Listening to Port: " + mySkServer.getLocalPort());


            while(true) {
                Socket clientSocket = mySkServer.accept();
                BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter pout = new PrintWriter(clientSocket.getOutputStream(), true);

                    pout.println(ClientNo);
                    pout.println("Choose the path where your songs are stored !");
                    int numberOfSong;
                    rootDirectory = buffin.readLine();
                    path = new ArrayList<>();
                    numberOfSong = Integer.parseInt(buffin.readLine());
                    for (int i = 0; i < numberOfSong; i++) {
                        path.add(buffin.readLine());
                    }

                    ClientModel connectedClient = new ClientModel(ClientNo,clientSocket.getInetAddress().toString(),path,rootDirectory);
                    connectedClients.add(connectedClient);
                    System.out.println("Connection request received");
                    Thread t = new Thread(new ClientHandler(clientSocket, ClientNo));
                    ClientNo++;
                    t.start();

            }

        } catch (SocketException e) {
            System.out.println("Connection Timed out");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
