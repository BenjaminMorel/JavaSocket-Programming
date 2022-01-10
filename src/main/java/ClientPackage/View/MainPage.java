package ClientPackage.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPage extends JFrame{

    private JFrame myFrame;
    private JButton quitButton;
    private ArrayList<JButton> allSongButton;


    public MainPage(){
        myFrame = new JFrame();
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);

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
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setForeground(new Color(255, 255, 255));
        quitButton.setBackground(new Color(0,0,0));

        myFrame.add(quitButton);
    }

}
