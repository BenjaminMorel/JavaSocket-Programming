package ServeurPackage.Serveur;

import ClientPackage.Model.ClientModel;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private Socket clientSocketOnServer;
    boolean IsStillRunning = true;
    private ArrayList<ClientModel> connectedClients;
    private int wantedClient = -2;
    private int index;
    //Constructor
    public ClientHandler(Socket clientSocketOnServer,int index)
    {
        this.clientSocketOnServer = clientSocketOnServer;
        this.connectedClients = Server.connectedClients;
        this.index = index;

    }
    //overwrite the thread run()
    public void run() {
        System.out.println("-----------------------------------");
        System.out.println("Socket is available for connection" + clientSocketOnServer);
        System.out.println("-----------------------------------");

        try {
            PrintWriter pout = new PrintWriter(clientSocketOnServer.getOutputStream(), true);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));

            //the server send the index of the client inside the array list

            while(wantedClient == -2 ) {


                //the serveur send the number of connected client to the client
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
                    System.out.println(wantedClient + " choix du client");
                        File RootDirectory = new File("D:\\Spotify");
                        File[] allSong = RootDirectory.listFiles();

                        System.out.println("Sending number of song");
                        pout.println(allSong.length);

                        for (File song : allSong) {
                            pout.println(song.getName());
                        }

                        while (IsStillRunning) {
                            System.out.println("Waiting for song name");
                            String fileName = buffin.readLine();
                            System.out.println("Song name is : " + fileName);


                            File songToPlay = new File(RootDirectory + "\\" + fileName);

                            long size = Files.size(Paths.get(RootDirectory + "\\" + fileName));

                            byte[] myByteArray = new byte[(int) size];

                            BufferedInputStream inputBuffer = new BufferedInputStream(new FileInputStream(songToPlay));


                            inputBuffer.read(myByteArray, 0, myByteArray.length);

                            OutputStream os = null;

                            os = clientSocketOnServer.getOutputStream();

                            System.out.println("Server will send the song");
                            System.out.println(clientSocketOnServer.getOutputStream());
                            System.out.println(myByteArray.length + " longeur song");
                            os.write(myByteArray, 0, myByteArray.length);
                            os.flush();
                            System.out.println("SERVER HAS SEND THE SONG");
                        }

              //          System.out.println("Now dying client " + clientNumber);
                        clientSocketOnServer.close();
                        pout.close();


                    // créer boucle while qui termine l'envoie quand on lui dit et qui se remet en mode écoute
                }
            }

            } catch(SocketException e){
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