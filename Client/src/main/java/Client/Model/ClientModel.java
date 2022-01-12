package Client.Model;

public class ClientModel {

    private String IPClient;
    private String clientName;
    private boolean isConnected;

    public ClientModel(String clientName, String IPClient,Boolean isConnected){
        this.IPClient = IPClient;
        this.clientName = clientName;
        this.isConnected = isConnected;
    }
    /**
     * Default constructor used for JSON File reading/writing
     */
    public ClientModel() {
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public String getclientName() {
        return clientName;
    }

    public String getIPClient() {
        return IPClient;
    }

}
