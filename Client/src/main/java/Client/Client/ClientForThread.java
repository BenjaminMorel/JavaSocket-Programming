package Client.Client;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import Client.Model.ClientModel;
import Client.View.*;

import javax.swing.*;

public class ClientForThread {

    private ArrayList<ClientModel> connectedClient = new ArrayList<>();

    public ClientForThread(){

    }

     public void RunClientForThread(){
         InetAddress serverAddress;
         String serverName = "192.168.137.46";

         try {
             serverAddress = InetAddress.getByName(serverName);
             Socket mySocket = new Socket(serverAddress, 45000);
             BufferedReader buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
             PrintWriter pout = new PrintWriter(mySocket.getOutputStream(), true);

             //The server sends a message to the client to ask him to write his name

            createNameInput(pout);

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

    /**
     * First graphical part of the application where it requires the user to write his name
     * @param pout
     */
     public void createNameInput(PrintWriter pout){
        JFrame myFrame =new JFrame();
        JTextField yourName =new JTextField();
        JLabel myLabel = new JLabel("Enter your name :");
        JButton sendName = new JButton("Send");
        myFrame.setLayout(new FlowLayout());

        yourName.setColumns(10);

        myFrame.setVisible(true);
        myFrame.setSize(150,150);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(myLabel);
        myFrame.add(yourName);
        myFrame.add(sendName);

         sendName.addActionListener(e -> {
             pout.println(yourName.getText());
             myFrame.dispose();
         });
     }
}






