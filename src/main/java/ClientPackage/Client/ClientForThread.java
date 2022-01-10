package ClientPackage.Client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import ClientPackage.Model.ClientModel;
import ClientPackage.View.*;

public class ClientForThread {

    private int nbConnectedClient;
    private ArrayList<ClientModel> connectedClient = new ArrayList<>();

    public ClientForThread(){

    }

     public void RunClientForThread(){
         System.out.println("New ClientPackage.Client created");
         InetAddress serverAddress;
         String serverName = "127.0.0.1";

         try {
             serverAddress = InetAddress.getByName(serverName);
             Socket mySocket = new Socket(serverAddress, 45000);
             BufferedReader buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
             PrintWriter pout = new PrintWriter(mySocket.getOutputStream(), true);

             // server send a message to ask us to write our name
             System.out.println(buffin.readLine());
             Scanner scan = new Scanner(System.in);
             String name = scan.nextLine();
             pout.println(name);

             int index = Integer.parseInt(buffin.readLine());
             //Create the first graphic page with all disponible client
             ClientChoice myMainPage = new ClientChoice(mySocket,index);

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






