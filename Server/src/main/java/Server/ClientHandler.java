package Server;

import Server.Log.ServerLogging;

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

    /**
     * Constructor of the ClientHandler Class
     * @param clientSocketOnServer
     * @param index
     * @throws IOException
     */
    public ClientHandler(Socket clientSocketOnServer,int index) throws IOException {
        this.clientSocketOnServer = clientSocketOnServer;
        this.connectedClients = Server.connectedClients;
        this.index = index;
         myLogger = new ServerLogging();
    }

    /**
     * Overwrite the thread run()
     */
    @Override
    public void run() {

        try {
            PrintWriter pout = new PrintWriter(clientSocketOnServer.getOutputStream(), true);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));

            //The server sends the number of connected client to the client
            while(wantedClient == -2 ) {
                IsStillRunning = true;
                pout.println(connectedClients.size());

                //The server sends every client information to the other client
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
                        //Path of te file where our musics are stored
                //        File RootDirectory = new File("/VSfy/MyMusic");
                     File RootDirectory = new File("D:\\Spotify");
                        File[] allSong = RootDirectory.listFiles();

                        pout.println(allSong.length);

                        for (File song : allSong) {
                            pout.println(song.getName());
                        }

                        while (IsStillRunning) {
                            String fileName = buffin.readLine();
                            if(fileName.equals("quit")){
                                IsStillRunning= false;
                                wantedClient = -2;
                                break;
                            }

//                           File songToPlay = new File(RootDirectory + "/" + fileName);
//
//                            long size = Files.size(Paths.get(RootDirectory + "/" + fileName));
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
                    // créer boucle while qui termine l'envoie quand on lui dit et qui se remet en mode écoute
                }
            }

            } catch(SocketException e){
            System.out.println(Server.TEXT_RED + connectedClients.get(index).getclientName() + " with IP " + connectedClients.get(index).getIPClient() + " has closed his connection to the server" + Server.TEXT_RESET);
            myLogger.getMyLogger().log(Level.INFO,"Client " + connectedClients.get(index).getclientName() + " has closed his connection to the server","Client " + connectedClients.get(index).getclientName() + " has closed his connection to the server");
                connectedClients.get(index).setConnected(false);
                Server.Save();
            try {
                Server.Read();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch(IOException e){
            myLogger.getMyLogger().log(Level.SEVERE,"============ERROR============" + e);
                e.printStackTrace();
            }
    }

    /**
     * Update the array list when refreshing the page
     */
    public void updateConnectedClientValue(){
        connectedClients = Server.connectedClients;
    }
}