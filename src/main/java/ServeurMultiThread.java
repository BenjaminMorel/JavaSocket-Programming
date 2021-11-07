import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class ServeurMultiThread {

    public static void main(String[] args) {

        Socket srvSocket = null ;
        InetAddress localAddress = null;
        ServerSocket mySkServer;
        PrintWriter pout;
        String interfaceName = "lo";
        int ClientNo = 1;

        try {

            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();

                if (!ia.isLinkLocalAddress()) {
                    if (!ia.isLoopbackAddress()) {
                        System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
                        localAddress = ia;
                    }
                }
            }

            mySkServer = new ServerSocket(45000, 10, localAddress);
            System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
            System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
            System.out.println("Listening to Port :" + mySkServer.getLocalPort());

            while(true) {
                Socket clientSocket = mySkServer.accept();
                System.out.println("Connection request received");
                Thread t = new Thread(new ClientHandler(clientSocket, ClientNo));
                ClientNo++;
                t.start();

            }

        }catch (SocketException e) {
            System.out.println("Connection Timed out");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
