package Client.View;

import Client.Controller.AudioController;
import Client.Model.ClientModel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ClientChoice {

    private JFrame myFrame;
    private JButton musicButton;
    private JButton refreshButton;
    private AudioController myProgram;
    private JButton uploadButton;

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
            JLabel myLabel = new JLabel(connectedClient.get(i).getclientName() + "  -  " + connectedClient.get(i).getIPClient().substring(1));
            myLabel.setFont(new Font("Android",Font.PLAIN, 16));
            if (i != index){
                if(connectedClient.get(i).getIsConnected()){
                    myLabel.setForeground(Color.green);
                } else{
                    myLabel.setForeground(Color.red);
                }
            } else{
                myLabel.setOpaque(true);
                myLabel.setBackground(Color.black);
                myLabel.setForeground(Color.white);
            }
            panel.add(myLabel);
            myFrame.add(panel);
        }

        uploadButton = new JButton("Upload Song");
        uploadButton.setBackground(Color.GRAY);
        uploadButton.addActionListener(e -> {
            try {
                uploadNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        refreshButton = new JButton("Refresh");
        myFrame.add(new JLabel());
        myFrame.add(uploadButton);
        myFrame.add(refreshButton);

        musicButton.addActionListener(e ->
        {
            pout.println("0");
            try {
                myFrame.dispose();
                myProgram = new AudioController(mySocket,index);
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

    public void uploadNewFile() throws IOException {

        JFileChooser myFileChooser = new JFileChooser();

        myFileChooser.setAcceptAllFileFilterUsed(false);
        myFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f != null && f.isDirectory()){
                    return true;
                }
                if(f != null && !f.getName().toUpperCase().endsWith(".WAV")){
                    return false;
                }
                return  true;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        myFileChooser.showOpenDialog(myFrame);
        File songToUpload = myFileChooser.getSelectedFile();
        long fileSize = Files.size(Paths.get(songToUpload.getPath()));
        pout.println("2");
        pout.println(songToUpload.getName());
        pout.println(fileSize);

        byte [] myByteArray = new byte[(int)fileSize];
        BufferedInputStream inputBuffer = new BufferedInputStream(new FileInputStream(songToUpload));
        inputBuffer.read(myByteArray, 0, myByteArray.length);
        OutputStream os = mySocket.getOutputStream() ;
        os.write(myByteArray, 0, myByteArray.length);
        os.flush();
        ClientChoice myMainPage = new ClientChoice(mySocket, index);

    }

}
