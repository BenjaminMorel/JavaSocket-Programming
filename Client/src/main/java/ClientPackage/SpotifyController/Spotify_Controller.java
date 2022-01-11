package ClientPackage.SpotifyController;

import ClientPackage.View.MainPage;
import ClientPackage.View.SongPage2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Spotify_Controller {

    // Communication avec le serveur
    private BufferedReader buffin;
    private PrintWriter pout;
    private InputStream is;
    private Socket mySocket;
    private AudioPlayer player;

    //partie visuel
    private MainPage mainPage;
    private SongPage2 songPage;

    //Data
    private int numberOfSong;
    private int actualSong;
    private ArrayList<JButton> allSongButton;

    public Spotify_Controller(Socket mySocket,int index) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        this.mySocket = mySocket;
        this.buffin = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        this.pout = new PrintWriter(mySocket.getOutputStream(), true);
        this.is = new BufferedInputStream(mySocket.getInputStream());
        mainPage = new MainPage(mySocket,index);

        setAllbuttonNameAndAction();
    }

    //Method to create an array list of Jbutton
    //Set the action on the JButton to send the name of the song to the server
    //Then send this array list to the view MainPage
    public void setAllbuttonNameAndAction() throws IOException {
        numberOfSong = Integer.parseInt(buffin.readLine());
        allSongButton = new ArrayList<>();

        for(int i = 0; i < numberOfSong; i++){
            JButton songButton = new JButton(buffin.readLine()) ;
            int position = i;
            songButton.addActionListener(e -> {
                try {
                    actionForTheButton(songButton, position);
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (LineUnavailableException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }
            });
            allSongButton.add(songButton);
        }
        mainPage.setAllButton(allSongButton);
    }

    public void actionForTheButton(JButton songButton,int position) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        pout.println(songButton.getText());
        actualSong = position;
        songPage = new SongPage2(is,songButton.getText(),position);
        JButton next_button = songPage.getNext_Button();
        JButton previous_button = songPage.getPrevious_Button();

        next_button.addActionListener(e -> {
            try {
                nextAndPreviousButton(position+1);
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            }
        });
        previous_button.addActionListener(e -> {
            try {
                nextAndPreviousButton(position-1);
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            }
        });
    }


    public void nextAndPreviousButton(int positionNextSong) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioPlayer player = songPage.getPlayer();
        JFrame songpageFrame = songPage.getMyFrame();
        songpageFrame.dispose();
        player.pause();
        player = null;
        actionForTheButton(allSongButton.get(positionNextSong),positionNextSong);
    }


}
