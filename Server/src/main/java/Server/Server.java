package Server;

import Server.Storable.JSONStorage;
import Server.Log.ServerLogging;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;

public class Server {

    public static ArrayList<ClientModel> connectedClients = new ArrayList<>();
    public static JSONStorage storage = new JSONStorage();
    public static File rootFile = new File("/ServerSocket/myFile.json");

    public static void main(String[] args) throws IOException {

        ServerSocket mySkServer;
        InetAddress localAddress = null;
        String interfaceName = "wlan0";
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

            //Load JSON File and create array list of ClientModel
            Read();

            //The clients are disconnected when they close their application, but if the server crashes the clients remain with "connected" status, to avoid that we disconnect them all at first
            disconnectClients();

            while(true) {
                Socket clientSocket = mySkServer.accept();
                BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter pout = new PrintWriter(clientSocket.getOutputStream(), true);
                boolean IsOnList = false;
                    pout.println("Write down your name !");
                    String clientName = buffin.readLine();
                    int index = 0;

                    String clientIP = clientSocket.getInetAddress().toString();
                    for (int i = 0; i< connectedClients.size(); i++){
                        if (connectedClients.get(i).getIPClient().equals(clientIP)){
                            connectedClients.get(i).setClientName(clientName);
                            connectedClients.get(i).setConnected(true);
                            IsOnList = true;
                            index = i;
                            break;
                        }
                    }

                    if(!IsOnList){
                        //Add new ClientModel to the array list
                        connectedClients.add(new ClientModel(clientName,clientIP,true));
                        //Save clients on JSON File
                        Save();
                        //Load JSON File to get a refresh of the array list of ClientModel
                        Read();
                        index = connectedClients.size()-1;
                    }

                    pout.println(index);
                    myLogger.getMyLogger().log(Level.INFO, connectedClients.get(index).getclientName() + " has succesfully connected", connectedClients.get(index).getclientName() + " has succesfully connected");
                    Thread t = new Thread(new ClientHandler(clientSocket,index));
                    t.start();
                    displayCurrentUsers();
            }

        } catch (SocketException e) {
            System.out.println("Connection Timed out");
            //Severe log for exception
            myLogger.getMyLogger().log(Level.SEVERE,"ERROR SEVERE",e);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the JSON File to get the list of all connected clients and set the static variable
     * @throws IOException
     */
    public static void Read() throws IOException {
        connectedClients = storage.read(rootFile);
    }

    /**
     * Save all connected clients into the JSON file
     */
    public static void Save(){
        storage.Write(rootFile, connectedClients);
    }

    /**
     * Put all clients "connected" status on false
     */
    public static void disconnectClients() {
        for (int i = 0; i < connectedClients.size(); i++) {
            connectedClients.get(i).setConnected(false);
        }
    }

    /**
     * Method to display the list of all connected users currently
     */
    public static void displayCurrentUsers(){
        for(ClientModel model : connectedClients){
            if(model.getIsConnected()) {
                System.out.println(model.getclientName() + " is connected with ip : " + model.getIPClient());
            }
        }
    }
}
