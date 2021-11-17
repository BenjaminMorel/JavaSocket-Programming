package Client;

import Client.ClientForThread;

public class Client2 {
    public static void main(String[] args) {
        ClientForThread client = new ClientForThread();
        client.RunClientForThread("THIS IS CLIENT 2");
    }
}
