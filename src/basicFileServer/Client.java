package basicFileServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	static String folder = "/home/victor/Bureau/Applications réparties/ClientFiles/";
	static String host = "127.0.0.1";
	static int port = 1234;

	public static void main(String args[]) {
		System.out.println("PROGRAMME CLIENT");
		
		Socket server;
		
		InputStream is;
		DataInputStream dis;
		
		OutputStream os;
		DataOutputStream dos;
		
		try {
			server = new Socket(host, port);
			System.out.println("Connected to " + server.getInetAddress());
			
			// Saisie de la requête
			Scanner sc = new Scanner(System.in);
		  System.out.println("Request file? ");
		  String filename = sc.nextLine();
			
			// Emission de la requête
		  os = server.getOutputStream();
			dos = new DataOutputStream(os);
		  dos.writeInt(filename.length());
			dos.write(filename.getBytes());
			System.out.println("File request sent. Waiting for answer...");
			
			// Réception du code-réponse à la requête
		  is = server.getInputStream();
		  dis = new DataInputStream(is);
			int length_in = dis.readInt();
			byte[] b_in = new byte[length_in];
			int nread = 0;
			int num = 0;
			while (nread < length_in) {
				num = dis.read(b_in, nread, length_in-nread);
				if (num < 0) {
					return;
				}
				nread += num;
			}
			String reply = new String(b_in);
			//System.out.println(reply.substring(0,3)); // DEBUG
			//System.out.println(reply.substring(0,3).equals("404")); // DEBUG

			// Traitement du code de réponse
			if (reply.substring(0,3).equals("404")) {
				System.out.println("ERROR 404: File not found");
			}
			else if (reply.equals("200 ")) {
				System.out.println("200: File found");
				System.out.println("Downloading...");
				
				is = server.getInputStream();
			  dis = new DataInputStream(is);
				length_in = dis.readInt();
				b_in = new byte[length_in];
				nread = 0;
				num = 0;
				while (nread < length_in) {
					num = dis.read(b_in, nread, length_in-nread);
					if (num == -1) {
						return;
					}
					nread += num;
				}

				FileWriter fstream = new FileWriter(filename);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(b_in.toString());;
		    out.close();
			}
			
			server.close();
			dos.close();
			dis.close();
			sc.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
