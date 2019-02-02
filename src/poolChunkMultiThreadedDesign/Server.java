package poolChunkMultiThreadedDesign;

import java.io.*;
import java.net.*;

public class Server {
	
	int port;
	String folder;
	int numberThread;
	
	Server(int port, String folder, int nbT) {
		this.port = port ;
		this.folder = folder ;
		numberThread = nbT ;
	}
	
	public void start() throws IOException, InterruptedException {
		
		System.out.println("PROGRAMME SERVEUR (CHUNK)");
		
		ClientBuffer buffer = new ClientBuffer(numberThread);
		
		for (int i = 1 ; i<=numberThread ; i++) {
			ServerInstance thread = new ServerInstance(i, port, folder, buffer);
			thread.start();
		}
		
		while(true) {
				ServerSocket server;
				server = new ServerSocket(port);
				
				Socket client;
				client = server.accept();
				buffer.put(client);
				
				server.close();	
		}
	}
	
	public static void main (String args[]) throws IOException, InterruptedException {
		Server server = new Server(1234, "ServerFiles/", 10);
		server.start();
	}

}