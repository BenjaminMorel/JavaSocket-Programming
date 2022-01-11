package Serveur.Log;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerLogging {

    Logger myLogger;
    FileHandler myFileHandler;

    public ServerLogging() throws IOException {
        myLogger =  Logger.getLogger("ServerLogger");


        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];

        System.out.println(month);
        myFileHandler = new FileHandler(  "/ServerSocket/" + month + ".log",true);

        myLogger.addHandler(myFileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        myFileHandler.setFormatter(formatter);
    }


    public Logger getMyLogger(){
        return myLogger;
    }
}
