package poolBasedMultiThreadedDesign;

import java.io.*;
import java.net.*;

public class Server {
	
	static int port = 1234 ;
	static String folder = "/home/victor/Bureau/Applications réparties/ServerFiles/";
	static int numberThread = 10 ;

	public static void main(String args[]) throws IOException {
		System.out.println("PROGRAMME SERVEUR");
		
		ServerSocket server;
		server = new ServerSocket(port);
		
		for (int i = 1 ; i<=numberThread ; i++) {
			ServerInstance thread = new ServerInstance(i, port, folder, server);
			thread.start();
		}

	}

}