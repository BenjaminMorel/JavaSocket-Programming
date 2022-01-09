package Serveur;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {

    private Socket clientSocketOnServer;
    private int clientNumber;
    boolean IsStillRunning = true;

    //Constructor
    public ClientHandler(Socket clientSocketOnServer, int clientNo)
    {
        this.clientSocketOnServer = clientSocketOnServer;
        this.clientNumber = clientNo;

    }
    //overwrite the thread run()
    public void run() {
        System.out.println("-----------------------------------");
        System.out.println("Client Nr " + clientNumber + " is connected");
        System.out.println("Socket is available for connection" + clientSocketOnServer);
        System.out.println("-----------------------------------");

        try{
            PrintWriter pout = new PrintWriter(clientSocketOnServer.getOutputStream(), true);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocketOnServer.getInputStream()));

            File RootDirectory = new File("D:\\Spotify");
            File[] allSong = RootDirectory.listFiles();

            System.out.println("Sending number of song");
            pout.println(allSong.length);

            for (File song : allSong) {
                pout.println(song.getName());
            }

            while(IsStillRunning) {
                System.out.println("Waiting for song name");
                String fileName = buffin.readLine();
                System.out.println("Song name is : " + fileName);
      //          pout.println(fileName);
//                if(fileName.equals("quit")){
//                    pout.println(fileName);
//                    IsStillRunning = false;
//                    break;
//                }

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

                System.out.println("Now dying client " + clientNumber);
                clientSocketOnServer.close();
                pout.close();

                // créer boucle while qui termine l'envoie quand on lui dit et qui se remet en mode écoute


        } catch (SocketException e) {
            System.out.println("Connection Timed out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}