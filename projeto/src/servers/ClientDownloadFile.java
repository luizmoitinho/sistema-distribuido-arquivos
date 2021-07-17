package servers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import utils.Server;

public class ClientDownloadFile extends Thread {

    String ip, file;
    int port;
    private final JButton button;
    private int fileSize;
    private final String pathToSave;

    public ClientDownloadFile(Server server, String fileToDownload, String pathToSave, JButton button) {
        this.ip = server.ip;
        this.port = Integer.parseInt(server.port);
        this.fileSize = Integer.parseInt(server.size);
        this.file = fileToDownload;
        this.pathToSave = pathToSave;
        this.button = button;
        this.button.setEnabled(false);
    }

    public void run() {
        try {
            String path = "/files/" + this.port + "/" + file;
            Socket clientSocket = new Socket(ip, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(path + '\n');

            // Criando arquivo que sera recebido pelo servidor
            System.out.println(pathToSave);
            
            FileOutputStream fileOut = new FileOutputStream(pathToSave);
            
            // Criando canal de transferencia
            InputStream socketIn = clientSocket.getInputStream();
            byte[] cbuffer = new byte[1024];
            int bytesRead;
            double sumCount = 0.0;
            
            while ((bytesRead = socketIn.read(cbuffer)) != -1) {
                fileOut.write(cbuffer, 0, bytesRead);
                sumCount += bytesRead;
                button.setText("Baixando... " + String.format("%.2f", (sumCount / fileSize * 100.0))  + "%");
            }
            fileOut.close();
            button.setEnabled(true);
            
            button.setText("Baixando... 100%");
            String mensagem = "Arquivo baixado com sucesso!\n";
            JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            button.setText("Baixar");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
