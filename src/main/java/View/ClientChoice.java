package View;

import Client.ClientForThread;
import Controller.SpotifyController.Spotify_Controller;
import Model.ClientModel;

import javax.naming.ldap.SortKey;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientChoice {

    private JFrame myFrame;
    private JButton serveurButton;
    private JButton refreshButton;
    private Spotify_Controller myProgram;
    private ArrayList<JButton> allClient = new ArrayList<>();

    private ArrayList<ClientModel> connectedClient = new ArrayList<>();
    private int IDClient;

    private Socket mySocket;
    private PrintWriter pout;
    private BufferedReader buffin;

    public ClientChoice(Socket mySocket, ArrayList<ClientModel> connectedClient,int IDClient) throws IOException {
        this.connectedClient = connectedClient;
        this.IDClient = IDClient;
        this.mySocket = mySocket;
        this.buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        this.pout = new PrintWriter(mySocket.getOutputStream(), true);

        myFrame = new JFrame();
        myFrame.setVisible(true);
        myFrame.setTitle("Client " + IDClient);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new GridLayout(connectedClient.size()+2,1));

        serveurButton = new JButton("Serveur");
        myFrame.add(serveurButton);



        for(int i = 0; i< connectedClient.size(); i++){
            JButton myClientButton;
            if(connectedClient.get(i).getIDClient() == IDClient) {
                myClientButton = new JButton( "Client :" + String.valueOf(connectedClient.get(i).getIDClient()) + " (my songs)");
            }else{
                myClientButton = new JButton( "Client :" + String.valueOf(connectedClient.get(i).getIDClient()));
            }
            myFrame.add(myClientButton);
            allClient.add(myClientButton);

        }
        refreshButton = new JButton("Refresh");
        myFrame.add(refreshButton);

        serveurButton.addActionListener(e ->
        {
            pout.println("0");
            try {
                myProgram = new Spotify_Controller(mySocket);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }
        });

        refreshButton.addActionListener(e -> {
            pout.println(-1 + "");
            myFrame.dispose();

            ClientForThread refreshPaged = new ClientForThread(IDClient);
            refreshPaged.RefreshClientForThread();

        });

        for(int i = 0; i< connectedClient.size(); i++){
            int finalI = i;
            allClient.get(i).addActionListener(e ->
            {pout.println(connectedClient.get(finalI).getIDClient());
                try {
                    myProgram = new Spotify_Controller(mySocket);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                }
            });
        }




    }
}
