package Client.View;

import Client.SpotifyController.Spotify_Controller;
import Client.Model.ClientModel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientChoice {

    private JFrame myFrame;
    private JButton musicButton;
    private JButton refreshButton;
    private Spotify_Controller myProgram;

    private ImageIcon connectedImage = new ImageIcon("src/main/java/ClientPackage/Images/connected.png");
    private ImageIcon disconnectedImage = new ImageIcon("src/main/java/ClientPackage/Images/disconnected.png");

    private int index;
    private int sizeClientList;
    private ArrayList<ClientModel> connectedClient = new ArrayList<>();
    private Socket mySocket;
    private PrintWriter pout;
    private BufferedReader buffin;

    public ClientChoice(Socket mySocket, int index) throws IOException {
        this.index = index;
        this.mySocket = mySocket;
        this.buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        this.pout = new PrintWriter(mySocket.getOutputStream(), true);

        sizeClientList = Integer.parseInt(buffin.readLine());
        for(int i = 0; i < sizeClientList; i++){
            boolean IsConnected = Boolean.valueOf(buffin.readLine());
            String name = buffin.readLine();
            String IP = buffin.readLine();
            connectedClient.add(new ClientModel(name,IP,IsConnected));
        }

        myFrame = new JFrame();
        myFrame.setVisible(true);
        myFrame.setTitle(connectedClient.get(index).getclientName());
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new GridLayout(connectedClient.size()+5,1));

        musicButton = new JButton("Listen to music");
        myFrame.add(musicButton);

        JLabel labelAllUsers = new JLabel("List of users");
        labelAllUsers.setFont(new Font("Android", Font.BOLD, 20));
        myFrame.add(labelAllUsers);

        for(int i = 0; i < connectedClient.size(); i++){
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            JLabel myLabel = new JLabel(connectedClient.get(i).getclientName() + "  -  " + connectedClient.get(i).getIPClient());
//            JLabel clientStatus = new JLabel();

            if (i != index){
                if(connectedClient.get(i).getIsConnected()){
//                    clientStatus = new JLabel(connectedImage);
                    myLabel.setForeground(Color.green);
                } else{
//                    clientStatus = new JLabel(disconnectedImage);
                    myLabel.setForeground(Color.red);
                }
            } else{
                myLabel.setOpaque(true);
                myLabel.setBackground(Color.black);
                myLabel.setForeground(Color.white);
            }
//            panel.add(clientStatus);
            panel.add(myLabel);
            myFrame.add(panel);
        }

        refreshButton = new JButton("Refresh");
        myFrame.add(refreshButton);

        musicButton.addActionListener(e ->
        {
            pout.println("0");
            try {
                myFrame.dispose();
                myProgram = new Spotify_Controller(mySocket,index);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }
        });

        int finalIndex = index;
        refreshButton.addActionListener(e -> {
            pout.println("-1");
            myFrame.dispose();

            try {
                ClientChoice myMainPage = new ClientChoice(mySocket, finalIndex);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }
}
