package basicFileServer;

import java.io.*;
import java.net.*;

public class Client {
	
	static String folder = "/home/victor/Bureau/Applications réparties/ClientFiles/";
	static String host = "127.0.0.1";
	static int port = 1234;

	public static void main(String args[]) {
		System.out.println("PROGRAMME CLIENT");
		
		Socket server;
		
		InputStream is;
		DataInputStream dis;
		int lengthIS;
		
		OutputStream os;
		DataOutputStream dos;
		
		try {
			server = new Socket(host, port);
			System.out.println("Connected to " + server.getInetAddress());
			
			// Saisie de la requête
			
			
			// Emission de la requête
			
			
			// Réception de la réponse à la requête
			
			
			// Emission du nom de la machine
			os = server.getOutputStream();
			dos = new DataOutputStream(os);
			String name = "Cocomo";
			byte[] bOut = name.getBytes();
			dos.writeInt(bOut.length);
			dos.write(bOut);
			System.out.println("Envoi du nom " + name);
			
			// Réception du message "Hello <client_name>"
			is = server.getInputStream();
			dis = new DataInputStream(is);
			lengthIS = dis.readInt();
			byte[] bIn = new byte[lengthIS];
			
			int nread = 0;
			int num = 0;
			
			while(nread < lengthIS) {
				num = dis.read(bIn, nread, lengthIS);
				if (num == -1) {
					return;
				}
				nread += num;
			}
			System.out.print("Server said: " + new String(bIn));
			
			server.close();
			dos.close();
			dis.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
