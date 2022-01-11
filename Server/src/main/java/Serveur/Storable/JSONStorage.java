package Serveur.Storable;

import Serveur.ClientModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JSONStorage {

    public ArrayList<ClientModel> read (File myFile) throws IOException {

        //Verify if the file already exists
        if (!myFile.exists()) {
            myFile.createNewFile();
        }

        //Create an array list and read the elements
        ObjectMapper mapper = new ObjectMapper();
        ClientModel[] temp;
        ArrayList<ClientModel> connectedClients = new ArrayList<>();

        try {
            temp = mapper.readValue(myFile, ClientModel[].class);
            connectedClients = new ArrayList<>(Arrays.asList(temp));

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Return the array of connected clients
        return connectedClients;
    }

    public void Write(File myFile, ArrayList<ClientModel> connectedClients) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            //Save values from arraylist into the file
            mapper.writeValue(myFile, connectedClients);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
