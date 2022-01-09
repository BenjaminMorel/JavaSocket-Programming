package Client;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import Controller.SpotifyController.Spotify_Controller;
import Serveur.AudioPlayer;
import View.*;

public class ClientForThread {


     public void RunClientForThread(String title){
         System.out.println("New Client created");
         InetAddress serverAddress;
         String serverName = "127.0.0.1";

         try {
             serverAddress = InetAddress.getByName(serverName);
             Socket mySocket = new Socket(serverAddress, 45000);

             System.out.println("Creating the page");
             Spotify_Controller myProgram = new Spotify_Controller(mySocket);

             myProgram.setAllbuttonNameAndAction();

             boolean IsPlaying  = true;

             while(IsPlaying){

             }

             System.out.println("\nMessage read. Now dying...");
             mySocket.close();
         }catch (UnknownHostException e) {
             e.printStackTrace();
         }catch (ConnectException e) {
             e.printStackTrace();
         }catch (IOException e) {
             e.printStackTrace();
         } catch (UnsupportedAudioFileException e) {
             e.printStackTrace();
         } catch (LineUnavailableException e) {
             e.printStackTrace();
         }
     }
}






