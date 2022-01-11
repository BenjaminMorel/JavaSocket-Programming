package ClientPackage.View;

import ClientPackage.SpotifyController.Spotify_Controller;
import ClientPackage.Model.ClientModel;

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
        myFrame.setLayout(new GridLayout(connectedClient.size()+2,1));

        musicButton = new JButton("My music");
        myFrame.add(musicButton);

        for(int i = 0; i < connectedClient.size(); i++){
            JLabel  myLabel = new JLabel(connectedClient.get(i).getclientName() + " | " + connectedClient.get(i).getIPClient());
            myLabel.setOpaque(true);
            if(i != index){
                if(connectedClient.get(i).getIsConnected()){
                    myLabel.setBackground(Color.GREEN);
                }else{
                    myLabel.setBackground(Color.red);
                }
            }else{
                myLabel.setBackground(Color.BLUE);

            }
            myFrame.add(myLabel);
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
