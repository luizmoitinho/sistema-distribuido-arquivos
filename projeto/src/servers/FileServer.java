package servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utils;

public class FileServer {

	public static void main(String[] args) {
		ServerSocket server = Utils.createTempServer();
		System.out.println("file server listening to port: " + server.getLocalPort());
		
		FileServerMulticast fsm = new FileServerMulticast(server.getLocalPort());
		fsm.start();
		
		while (true) {
			try {
				Socket client = server.accept();
				FileServerClientSocket fscs = new FileServerClientSocket(client);
				fscs.start();
			} catch (IOException ex) {

			}
		}
	}
	
}
