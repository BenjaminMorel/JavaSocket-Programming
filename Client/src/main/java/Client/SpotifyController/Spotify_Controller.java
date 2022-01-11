package Client.SpotifyController;

import Client.View.MainPage;
import Client.View.SongPage2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Spotify_Controller {

    //Used to communicate with the server
    private BufferedReader buffin;
    private PrintWriter pout;
    private InputStream is;
    private Socket mySocket;
    private AudioPlayer player;

    //GUI Part
    private MainPage mainPage;
    private SongPage2 songPage;

    //Data elements
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

    /**
     * Method to create an array list of JButton
     * Set the action on the JButton to send the name of the song to the server
     * Then send this array list to the view MainPage
     * @throws IOException
     */
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

    /**
     * Set up actions for the different buttons of our page
     * @param songButton Button we want to put an action on
     * @param position Position of the button (for each music)
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
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

    /**
     * Set up for the 2 JButtons Next & Previous
     * @param positionNextSong
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void nextAndPreviousButton(int positionNextSong) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioPlayer player = songPage.getPlayer();
        JFrame songpageFrame = songPage.getMyFrame();
        songpageFrame.dispose();
        player.pause();
        player = null;
        actionForTheButton(allSongButton.get(positionNextSong),positionNextSong);
    }


}
