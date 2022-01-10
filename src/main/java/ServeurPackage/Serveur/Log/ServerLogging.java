package ServeurPackage.Serveur.Log;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ServerLogging {

    Logger myLogger;
    FileHandler myFileHandler;

    public ServerLogging() throws IOException {
        myLogger =  Logger.getLogger("ServerLogger");
        myFileHandler = new FileHandler();
    }
}
