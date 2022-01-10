package ServeurPackage.Serveur;

import ClientPackage.Model.ClientModel;
import ServeurPackage.Serveur.Log.ServerLogging;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;

public class Server {

    public static ArrayList<ClientModel> connectedClients = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        ServerSocket mySkServer;
        InetAddress localAddress = null;
        String interfaceName = "lo";
        ServerLogging myLogger = new ServerLogging();

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

            //Ajouter log serveur UP
            System.out.println("Listening to Port: " + mySkServer.getLocalPort());


            //appelle une methode qui va load le JSON et créer l'array list de modelCLient
            Read();


            while(true) {
                Socket clientSocket = mySkServer.accept();
                BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter pout = new PrintWriter(clientSocket.getOutputStream(), true);
                boolean IsOnList = false;
                   // pout.println(ClientNo);
                    pout.println("Write down your name !");
                    String clientName = buffin.readLine();
                    int index = 0;

                    String clientIP = clientSocket.getInetAddress().toString();
                    for(int i = 0; i< connectedClients.size(); i++){
                        if(connectedClients.get(i).getIPclient().equals(clientIP)){
                            System.out.println("Client already on the list");
                            connectedClients.get(i).setClientName(clientName);
                            connectedClients.get(i).setConnected(true);
                            IsOnList = true;
                            index = i;

                            break;
                        }
                    }

                    if(!IsOnList){
                        connectedClients.add(new ClientModel(clientName,clientIP,true));
                        //Appelle method save
                        Save();
                        Read();
                        index = connectedClients.size()-1;
                    }

                    System.out.println("Connection request received");
                    Thread t = new Thread(new ClientHandler(clientSocket,index));
                    t.start();
            }

            // écrire dans le JSON

        } catch (SocketException e) {
            System.out.println("Connection Timed out");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Read(){

    }

    public static void Save(){
        // put all client connected on false
    }

}
