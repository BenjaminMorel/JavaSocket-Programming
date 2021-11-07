package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class Song {

    JButton songInfo;
    public static String mySongName = "";
    PrintWriter pout;

    public Song(String nameOfTheSong, PrintWriter pout){
        songInfo = new JButton(nameOfTheSong);
        this.pout = pout;
        // le bouton recuperer le PrintWriter afin depouvoir transmettre le nom du son a lire
        songInfo.addActionListener(e -> pout.println(songInfo.getText()));
    }

    public JButton GetSongInfo(){
        return songInfo ;
    }


}






