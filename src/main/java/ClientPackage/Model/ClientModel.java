package ClientPackage.Model;

import java.util.ArrayList;

public class ClientModel {

    private String IPclient;
    private String clientName;
    private boolean IsConnected;

    public ClientModel(String clientName, String IPclient,Boolean isConnected){
        this.IPclient = IPclient;
        this.clientName = clientName;
        this.IsConnected = isConnected;
    }

    public boolean getIsConnected() {
        return IsConnected;
    }

    public void setConnected(boolean connected) {
        IsConnected = connected;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getclientName() {
        return clientName;
    }

    public void setIPclient(String IPclient) {
        this.IPclient = IPclient;
    }

    public String getIPclient() {
        return IPclient;
    }

}
