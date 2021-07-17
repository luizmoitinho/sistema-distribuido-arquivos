package servers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utils;

public class FileServerMulticast extends Thread {

    private int port;
    
    public FileServerMulticast(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            MulticastSocket mcs = new MulticastSocket(9000);
            InetAddress grp = InetAddress.getByName("239.0.0.1");
            mcs.joinGroup(grp);

            while (true) {
                try {
                    byte rec[] = new byte[256];
                    DatagramPacket pkg = new DatagramPacket(rec, rec.length);
                    mcs.receive(pkg);
                    String fromServerData = new String(pkg.getData(), 0, pkg.getLength());
                    
                    System.out.println("Dados recebidos: " + fromServerData);
                    
                    String[] fromServerDataArr = fromServerData.split(";");
                    
                    int mainServerSocketPort = Integer.parseInt((fromServerDataArr[0]));
                    String requestedFileName = fromServerDataArr[1];
                    
                    String path = "/files/"+ this.port + "/";
                    if (Utils.searchFiles(path,requestedFileName)) {
                        Socket client = new Socket("127.0.0.1", mainServerSocketPort);
                        String buffer = client.getInetAddress().toString() + ":" + this.port + ":" + Utils.getFileSize(path, requestedFileName) + "\n";
                        DataOutputStream outputBuffer = new DataOutputStream(client.getOutputStream());
                        System.out.println(buffer);
                        outputBuffer.writeBytes(buffer);
                    }

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro");
        }
    }
}
