package Client;

class Client1 {
    public static void main(String[] args) {
        ClientForThread client = new ClientForThread();
        client.RunClientForThread("I am client 1");
    }
}

class Client2 {
    public static void main(String[] args) {
        ClientForThread client = new ClientForThread();
        client.RunClientForThread("I am client 2");
    }
}