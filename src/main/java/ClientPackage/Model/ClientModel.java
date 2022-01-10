package ClientPackage.Model;

public class ClientModel {

    private String IPClient;
    private String clientName;
    private boolean isConnected;

    public ClientModel(String clientName, String IPClient,Boolean isConnected){
        this.IPClient = IPClient;
        this.clientName = clientName;
        this.isConnected = isConnected;
    }

    public ClientModel() {
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setConnected(boolean IsConnected) {
        this.isConnected = IsConnected;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getclientName() {
        return clientName;
    }

    public void setIPClient(String IPClient) {
        this.IPClient = IPClient;
    }

    public String getIPClient() {
        return IPClient;
    }

}
