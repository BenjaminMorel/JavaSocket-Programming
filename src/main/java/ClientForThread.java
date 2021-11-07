
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.*;
import View.MainPage;
import View.Song;

public class ClientForThread {

        final  String TEXT_RESET = "\u001B[0m";
        final String TEXT_BLACK = "\u001B[30m";
        final String TEXT_RED = "\u001B[31m";
        final String TEXT_GREEN = "\u001B[32m";
        final String TEXT_YELLOW = "\u001B[33m";
        final String TEXT_BLUE = "\u001B[34m";
        final String TEXT_PURPLE = "\u001B[35m";
        final String TEXT_CYAN = "\u001B[36m";
        final String TEXT_WHITE = "\u001B[37m";


     public void RunClientForThread(String title){
         System.out.println("New Client created");
         InetAddress serverAddress;
         String serverName = "127.0.0.1";

         try {
             serverAddress = InetAddress.getByName(serverName);
             Socket mySocket = new Socket(serverAddress, 45000);

             BufferedReader buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
             PrintWriter pout = new PrintWriter(mySocket.getOutputStream(), true);

             int nbSong = Integer.parseInt(buffin.readLine());

             MainPage myPage = new MainPage(title,pout,nbSong+1);
             for (int i = 0; i < nbSong; i++) {
                 Song mySong = new Song(buffin.readLine(), pout);
                 myPage.addSong(mySong);
             }
             myPage.showButton();
             while(myPage.GetContinuePlaying()){

             String fileName = buffin.readLine();
             if(fileName.equals("quit")){
                     break;
                 }


             System.out.println(fileName + " is playing");

             InputStream is = new BufferedInputStream(mySocket.getInputStream());

             AudioPlayer player = new AudioPlayer(is);


             player.play();

             System.out.println(((int) player.getSongLength() / 1000 + " durée du song"));
             Thread.sleep((int) player.getSongLength() / 1000);

             player.pause();
            }


             System.out.println("\nMessage read. Now dying...");
             mySocket.close();
         }catch (UnknownHostException e) {
             e.printStackTrace();
         }catch (ConnectException e) {
             e.printStackTrace();
         }catch (IOException | InterruptedException e) {
             e.printStackTrace();
         } catch (UnsupportedAudioFileException e) {
             e.printStackTrace();
         } catch (LineUnavailableException e) {
             e.printStackTrace();
         }
     }
}






