package ServeurPackage.Serveur;

import ClientPackage.Model.ClientModel;
import ServeurPackage.Serveur.Log.ServerLogging;
import ServeurPackage.Serveur.Storable.JSONStorage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static ArrayList<ClientModel> connectedClients = new ArrayList<>();
    public static JSONStorage storage = new JSONStorage();
    public static File rootFile = new File("D:\\myFile.json");

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
            disconnectClients();


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
                        if(connectedClients.get(i).getIPClient().equals(clientIP) && connectedClients.get(i).getclientName().equals(clientName)){
                      //      connectedClients.get(i).setClientName(clientName);
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
                    pout.println(index);
                    myLogger.getMyLogger().log(Level.INFO, connectedClients.get(index).getclientName() + " has succesfully connected", connectedClients.get(index).getclientName() + " has succesfully connected");
                    Thread t = new Thread(new ClientHandler(clientSocket,index));
                    t.start();
                    displayCurrentUsers();
            }

            //Disconnect all clients

            // écrire dans le JSON

        } catch (SocketException e) {
            System.out.println("Connection Timed out");
            myLogger.getMyLogger().log(Level.SEVERE,"ERROR SEVERE",e);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Read() throws IOException {
        //Get the connected clients and set the static variable
        connectedClients = storage.read(rootFile);
    }

    public static void Save(){
        //Save all connected clients into the file
        storage.Write(rootFile, connectedClients);
    }

    public static void disconnectClients() {
        //Put all clients connected on false
        for (int i = 0; i < connectedClients.size(); i++) {
            connectedClients.get(i).setConnected(false);
        }
    }

    public static void displayCurrentUsers(){
        for(ClientModel model : connectedClients){
            if(model.getIsConnected()) {
                System.out.println(model.getclientName() + " is connected with ip : " + model.getIPClient());
            }
        }
    }

}
