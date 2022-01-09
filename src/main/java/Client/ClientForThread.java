package Client;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import Controller.SpotifyController.Spotify_Controller;
import Model.ClientModel;
import Serveur.AudioPlayer;
import View.*;

public class ClientForThread {

    private int nbConnectedClient;
    private int IDClient = 0;
    private ArrayList<ClientModel> connectedClient = new ArrayList<>();

    public ClientForThread(){

    }
    public ClientForThread(int IDClient){
        this.IDClient = IDClient;
    }

     public void RunClientForThread(){
         System.out.println("New Client created");
         InetAddress serverAddress;
         String serverName = "127.0.0.1";

         try {
             serverAddress = InetAddress.getByName(serverName);
             Socket mySocket = new Socket(serverAddress, 45000);
             BufferedReader buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
             PrintWriter pout = new PrintWriter(mySocket.getOutputStream(), true);

             //send to the server if your are refreshing the page or not
                 pout.println("False");
                 IDClient = Integer.parseInt(buffin.readLine());


             System.out.println(buffin.readLine());
             Scanner scan = new Scanner(System.in);
   //          File rootDirectory = new File(scan.nextLine());
             File rootDirectory = new File("D:\\Client1");

             pout.println(rootDirectory.getPath());
             File[] allSongs = rootDirectory.listFiles();

             pout.println(allSongs.length);
             for(int i = 0; i < allSongs.length; i++){
                 pout.println(allSongs[i].getPath());
             }


             nbConnectedClient = Integer.parseInt(buffin.readLine());
             for(int i = 0; i < nbConnectedClient; i++){
                 connectedClient.add(new ClientModel(Integer.parseInt(buffin.readLine())));
             }

             //Create the first graphic page with all disponible client
             ClientChoice myMainPage = new ClientChoice(mySocket,connectedClient,IDClient);

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
         }
     }

    public void RefreshClientForThread(){
        InetAddress serverAddress;
        String serverName = "127.0.0.1";

        try {
            serverAddress = InetAddress.getByName(serverName);
            Socket mySocket = new Socket(serverAddress, 45000);
            BufferedReader buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            PrintWriter pout = new PrintWriter(mySocket.getOutputStream(), true);

            //send to the server if your are refreshing the page or not
            pout.println("True");
            pout.println(IDClient + "");

            nbConnectedClient = Integer.parseInt(buffin.readLine());
            for(int i = 0; i < nbConnectedClient; i++){
                connectedClient.add(new ClientModel(Integer.parseInt(buffin.readLine())));
            }

            //Create the first graphic page with all disponible client
            ClientChoice myMainPage = new ClientChoice(mySocket,connectedClient,IDClient);

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
        }
    }
}






