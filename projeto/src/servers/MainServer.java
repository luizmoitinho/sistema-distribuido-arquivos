package servers;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(8000);
            System.out.println("listening to port 8000");
            while(true) {
                Socket client = server.accept();
                ClientConnectionThread cct = new ClientConnectionThread(client);
                cct.start();
            }
        } catch (Exception e) {
            
        }
    }
}

