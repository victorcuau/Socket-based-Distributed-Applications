package basicMultiThreadedDesign;

import java.io.*;
import java.net.*;

public class Server {
	
	static int port = 1234 ;
	static String folder = "/home/victor/Bureau/Applications réparties/ServerFiles/";

	public static void main(String args[]) throws IOException {
		System.out.println("PROGRAMME SERVEUR");
		
		while (true) {
			// Le serveur est en attente de connexion
			
			ServerSocket server;
			Socket client;
			server = new ServerSocket(port);
			client = server.accept();
			// Un client est connecté
			ServerInstance thread = new ServerInstance(port, folder, client);
			thread.start();
			
			server.close();
		}
	}

}