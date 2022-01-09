package Model;

import View.ClientChoice;

import java.util.ArrayList;

public class ClientModel {

    private int IDClient;
    private String IPclient;
    private String rootDirectory;
    private ArrayList<String> path;

    public ClientModel(int IDClient, String IPclient, ArrayList<String> path,String rootDirectory){
        this.IDClient = IDClient;
        this.IPclient = IPclient;
        this.rootDirectory = rootDirectory;
        this.path = path;
    }

    public ClientModel(int IDClient){
        this.setIDClient(IDClient);
    }

    public void setIDClient(int IDClient) {
        this.IDClient = IDClient;
    }

    public void setIPclient(String IPclient) {
        this.IPclient = IPclient;
    }

    public void setrootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public int getIDClient() {
        return IDClient;
    }

    public String getIPclient() {
        return IPclient;
    }


    public String getRootDirectory() {
        return rootDirectory;
    }
    public ArrayList<String> getPath() {
        return path;
    }
}
