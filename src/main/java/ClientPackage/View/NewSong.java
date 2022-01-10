package ClientPackage.View;
import java.io.PrintWriter;

public class NewSong {

    private String songTitle = "";
    private PrintWriter pout;

    public NewSong(String songTitle, PrintWriter pout){
        this.songTitle = songTitle;
        this.pout = pout;
    }

    public String getSongTitle(){
        return songTitle;
    }
}
