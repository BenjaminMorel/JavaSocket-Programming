package Client.SpotifyController;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {

    Clip clip;

    String status = "play";

    AudioInputStream audioInputStream;
    static String filePath;

    public AudioPlayer(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Create AudioInputStream object

        audioInputStream = AudioSystem.getAudioInputStream(is);

        //Create clip reference
        clip = AudioSystem.getClip();

        //Open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(0);
    }

    /**
     * Method to start the audio (clip)
     */
    public void play() {
        clip.start();
//        this.currentFrame = this.clip.getMicrosecondPosition();
        status = "play";
    }

    /**
     * Method to pause the audio (clip)
     */
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
       // this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    /**
     * Method to change the status of the audio (clip)
     */
    public void changeStatus(){
        if(status.equals("paused")) {
            clip.start();
            status = "play";

        } else if(status.equals("play")){
            clip.stop();
            status = "paused";
        }
    }

    public long getSongLength(){
        return clip.getMicrosecondLength();
    }

    /**
     * Method to reset audio stream
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public long getCurrentFrame() {
        return clip.getMicrosecondPosition();
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    /**
     * Method to increase the audio (clip)
     */
    public void increaseVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(20f * (float) Math.log10(volume));
        gainControl.setValue(getVolume()+10);
    }

    /**
     * Method to diminue the audio (clip)
     */
    public void diminueVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(20f * (float) Math.log10(volume));
        gainControl.setValue(getVolume()-10);
    }

}