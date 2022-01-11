package ServeurPackage.Serveur;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MusicReader implements Runnable {

    private Socket clientSocket;
    private boolean IsStillRunning = true;

    public MusicReader(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            PrintWriter pout = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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

                os = clientSocket.getOutputStream();


                os.write(myByteArray, 0, myByteArray.length);
                os.flush();
                System.out.println("SERVER HAS SEND THE SONG");
            }

            //          System.out.println("Now dying client " + clientNumber);
            clientSocket.close();
            pout.close();


            // créer boucle while qui termine l'envoie quand on lui dit et qui se remet en mode écoute
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
