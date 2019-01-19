package babyStep;

import java.io.*;
import java.net.*;

public class Server {

	public static void main(String args[]) throws IOException {
		System.out.println("PROGRAMME SERVEUR");
		
		while (true) {
			// Le serveur est en attente de connexion
			
			ServerSocket server;
			Socket client;
			server = new ServerSocket(1234);
			client = server.accept();
			
			// Un client est connecté
			
			System.out.println("Client " + client.getInetAddress() + " connected.");
			
			InputStream is = client.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			OutputStream os = client.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			// Réception du nom du client
			int length_in = dis.readInt();
			byte[] b_in = new byte[length_in];
			int nread = 0;
			int num = 0;
			while (nread < length_in) {
				num = dis.read(b_in, nread, length_in-nread);
				if (num == -1) {
					return;
				}
				nread += num;
			}
			String client_name = new String(b_in);
			System.out.println("Client said: " + client_name);
			
			// Emission du message "Hello <client_name>"
			String message = "Hello " + client_name ;
			byte[] b_out = message.getBytes();
			dos.writeInt(b_out.length);
			dos.write(b_out);
			System.out.println("Reply: " + message);
			
			dis.close();
			dos.close();
			client.close();
			server.close();
		}
	}

}