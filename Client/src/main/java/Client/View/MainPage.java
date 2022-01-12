package Client.View;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainPage extends JFrame{

    private JFrame myFrame;
    private JButton quitButton;
    private ArrayList<JButton> allSongButton;
    private PrintWriter pout;
    private Socket mySocket;
    private int index;


    public MainPage(Socket mySocket,int index) throws IOException {
        myFrame = new JFrame();
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        this.mySocket = mySocket;
        this.index = index;
        pout = new PrintWriter(mySocket.getOutputStream(), true);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //Method to receive an array list of Jbutton and add each one in the page
    public void setAllButton(ArrayList<JButton> allSongButton){

        this.allSongButton = allSongButton;

        //Setting the layout to number of song +1 for the quit button
        myFrame.setLayout(new GridLayout(allSongButton.size()+1,1));

        for(int i = 0; i < allSongButton.size(); i++){
            myFrame.add(allSongButton.get(i));
        }
        quitButton = new JButton("Quit");

        quitButton.addActionListener(e -> {
            myFrame.dispose();
            pout.println("quit");
            try {
                ClientChoice myChoice = new ClientChoice(mySocket,index );
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        quitButton.setForeground(new Color(255, 255, 255));
        quitButton.setBackground(new Color(0,0,0));

        myFrame.add(quitButton);
    }
}
