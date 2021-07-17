package servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import utils.Utils;

public class ClientConnectionThread extends Thread {

    private final Socket s;

    public ClientConnectionThread(Socket s) {
        this.s = s;
    }


    @Override
    public void run() {
        try {
        	
            BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String filename = clientBuffer.readLine();
 
            ServerSocket server = Utils.createTempServer();
            System.out.println("Client Connection Thread listening to port: "+ server.getLocalPort());

            String bufferPackage = server.getLocalPort() + ";" + filename;
            byte[] b = bufferPackage.getBytes();

            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 9000);
            DatagramSocket ds = new DatagramSocket();
            ds.send(pkg);

            String responses = "";
            Socket client = null;

            SocketTimeoutThread stt = new SocketTimeoutThread(server);
            stt.start();

            System.out.println("waiting file");
            while (true) {
                try {
                    client = server.accept();
                    
                    BufferedReader fileServerBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String response = fileServerBuffer.readLine();
                    
                    System.out.println(response);
                    responses = responses + response + ";";
                } catch (SocketException e) {
                    break;
                }
            }
            System.out.println("stop waiting");

            OutputStream socketOut = s.getOutputStream();
            socketOut.write(responses.replaceAll("\n", "").replaceAll("/", "").concat("\n").getBytes());
            
            s.close();
            
        } catch (IOException ex) {

        }
    }
}
