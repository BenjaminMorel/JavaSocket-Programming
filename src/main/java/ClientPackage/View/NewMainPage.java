package ClientPackage.View;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class NewMainPage extends JFrame{

    private JFrame myFrame;
    private JButton quitButton;
    private ArrayList<JButton> allSong = new ArrayList<>();
    private boolean continuePlaying = true;
    private SongPage mySongPage;

    public NewMainPage(String title, PrintWriter pout, int sizeGridLayout, ArrayList<NewSong> allSong, Socket mySocket){
        myFrame = new JFrame();
        myFrame.setTitle(title);
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setLayout(new GridLayout(sizeGridLayout,1));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for(int i = 0; i < sizeGridLayout-1; i++){
            String songTitle = allSong.get(i).getSongTitle();
            JButton myJButton = new JButton(songTitle);
            myJButton.setBorderPainted(true);
            myJButton.setFocusPainted(false);
            myJButton.setContentAreaFilled(false);
            myJButton.addActionListener(e -> {
                pout.println(songTitle);
                try {
                    mySongPage = new SongPage(songTitle,mySocket);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            myFrame.add(myJButton);
        }

        quitButton = new JButton("Quit");
        quitButton.setForeground(new Color(255, 255, 255));
        quitButton.setBackground(new Color(0,0,0));

        myFrame.add(quitButton);

        quitButton.addActionListener(e -> {
            myFrame.dispose();
            pout.println("quit");
        });
    }

    public boolean GetContinuePlaying(){
        return continuePlaying;
    }

}
