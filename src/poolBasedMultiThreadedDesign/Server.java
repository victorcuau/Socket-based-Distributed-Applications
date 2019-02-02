package poolBasedMultiThreadedDesign;

import java.io.*;
import java.net.*;

public class Server {
	
	static int port = 1234 ;
	static String folder = "ServerFiles/";
	static int numberThread = 10 ;
	

	public static void main(String args[]) throws IOException {
		System.out.println("PROGRAMME SERVEUR");
		
		ClientBuffer buffer = new ClientBuffer(numberThread);
		
		
		
		for (int i = 1 ; i<=numberThread ; i++) {
			ServerInstance thread = new ServerInstance(i, port, folder, buffer);
			thread.start();
		}
		
		while(true) {
			try {
				ServerSocket server;
				server = new ServerSocket(port);
				
				Socket client;
				client = server.accept();
				buffer.put(client);
				
				server.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

}