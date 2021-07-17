package servers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServerClientSocket extends Thread {

    private Socket s;

    private int folder;

    public FileServerClientSocket(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader clientBuffer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String path = clientBuffer.readLine();
            
            System.out.println(path);

            Path currentRelativePath = Paths.get("");
            String url = currentRelativePath.toAbsolutePath().toString() + path;

            //File file = new File(url);
            //FileInputStream fileInputStream = new FileInputStream(file);

            FileInputStream fileIn = new FileInputStream(url);
            OutputStream socketOut = s.getOutputStream();
            // Criando tamanho de leitura
            byte[] cbuffer = new byte[1024];
            int bytesRead;
            // Lendo arquivo criado e enviado para o canal de transferencia
            while ((bytesRead = fileIn.read(cbuffer)) != -1) {
                socketOut.write(cbuffer, 0, bytesRead);
                socketOut.flush();
            }
            fileIn.close();
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("Erro");
        }
    }
}
