package servers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public String filename;
    private Socket client;

    public Client() throws IOException {
        setClient(new Socket("127.0.0.1", 8000));
    }

    public String[] search(String filename) throws IOException {
        DataOutputStream outputBuffer = new DataOutputStream(client.getOutputStream());
        outputBuffer.writeBytes(filename + '\n');
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String result = inFromServer.readLine();
        
        if ("".equals(result)) {
            return null;
        }

        return (String[]) result.split(";");

    }

    public void setClient(Socket c) {
        this.client = c;
    }

    public void getFile(String ip, int port, String file) {
        try {
            Socket clientSocket = new Socket(ip, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(file + '\n');
            String resposta = inFromServer.readLine();

            // Criando arquivo que sera recebido pelo servidor
            FileOutputStream fileOut = new FileOutputStream("file_" + file);
            // Criando canal de transferencia
            InputStream socketIn = clientSocket.getInputStream();
            byte[] cbuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = socketIn.read(cbuffer)) != -1) {
                fileOut.write(cbuffer, 0, bytesRead);
            }
            fileOut.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
