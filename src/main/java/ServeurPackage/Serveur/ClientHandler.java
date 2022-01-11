package ServeurPackage.Serveur;

import ClientPackage.Model.ClientModel;
import ServeurPackage.Serveur.Log.ServerLogging;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;

public class ClientHandler implements Runnable {

    private Socket clientSocketOnServer;
    boolean IsStillRunning = true;
    private ArrayList<ClientModel> connectedClients;
    private int wantedClient = -2;
    private int index;
    private ServerLogging myLogger;
    //Constructor
    public ClientHandler(Socket clientSocketOnServer,int index) throws IOException {
        this.clientSocketOnServer = clientSocketOnServer;
        this.connectedClients = Server.connectedClients;
        this.index = index;
         myLogger = new ServerLogging();

    }
    //overwrite the thread run()
    public void run() {

        try {
            PrintWriter pout = new PrintWriter(clientSocketOnServer.getOutputStream(), true);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));

            //the serveur send the number of connected client to the client

            while(wantedClient == -2 ) {

                pout.println(connectedClients.size());
                //The server send every client information to the other client
                for (int i = 0; i < connectedClients.size(); i++) {
                    pout.println(connectedClients.get(i).getIsConnected());
                    pout.println(connectedClients.get(i).getclientName());
                    pout.println(connectedClients.get(i).getIPClient());
                }
                int wantedClient = Integer.parseInt(buffin.readLine());
                if (wantedClient == -1) {
                    updateConnectedClientValue();
                    wantedClient = -2;
                } else {
                    System.out.println("Enter the page song");

                        File RootDirectory = new File("D:\\Spotify");
                        File[] allSong = RootDirectory.listFiles();

                        pout.println(allSong.length);

                        for (File song : allSong) {
                            pout.println(song.getName());
                        }

                        while (IsStillRunning) {
                            String fileName = buffin.readLine();
                            if(fileName.equals("quit")){
                                System.out.println("quit received");
                                IsStillRunning= false;
                                break;
                            }

                            File songToPlay = new File(RootDirectory + "\\" + fileName);

                            long size = Files.size(Paths.get(RootDirectory + "\\" + fileName));

                            byte[] myByteArray = new byte[(int) size];

                            BufferedInputStream inputBuffer = new BufferedInputStream(new FileInputStream(songToPlay));


                            inputBuffer.read(myByteArray, 0, myByteArray.length);

                            OutputStream os = null;

                            os = clientSocketOnServer.getOutputStream();

                            os.write(myByteArray, 0, myByteArray.length);
                            os.flush();
                        }

                    System.out.println("out of the while running");
                        wantedClient = -2;
                    // créer boucle while qui termine l'envoie quand on lui dit et qui se remet en mode écoute
                }
            }

            } catch(SocketException e){
            myLogger.getMyLogger().log(Level.INFO,"Client " + connectedClients.get(index).getclientName() + " has closed his connection to the server","Client " + connectedClients.get(index).getclientName() + " has closed his connection to the server");
                connectedClients.get(index).setConnected(false);
                Server.Save();
            try {
                Server.Read();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch(IOException e){
                e.printStackTrace();
            }


    }

    public void updateConnectedClientValue(){
        connectedClients = Server.connectedClients;
    }
}