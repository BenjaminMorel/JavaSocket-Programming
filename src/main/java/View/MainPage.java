package View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainPage {

    JFrame myFrame;
    JButton quitButton;
    ArrayList<Song> allSong = new ArrayList<>();
    int songAlreadyShow = 0;
    boolean continuePlaying = true;

    public MainPage(String title, PrintWriter pout,int sizeGridLayout){
        myFrame = new JFrame();
        myFrame.setTitle(title);
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setLayout(new GridLayout(sizeGridLayout,1));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        quitButton = new JButton("quit");
        quitButton.setBackground(new Color(10,150,10));

        quitButton.addActionListener(e -> {
                continuePlaying = false;
                myFrame.dispose();
                pout.println("quit");
        });
    }

    public void addSong(Song newSong){
        allSong.add(newSong);
    }

    public void showButton(){
        for(Song song : allSong){
            myFrame.add(song.GetSongInfo());
        }
        myFrame.add(quitButton);
    }

    public String songName(){
        return null;
    }

    public boolean GetContinuePlaying(){
        return continuePlaying;
    }
    public ArrayList<Song> GetallSong(){
        return allSong;
    }


}
