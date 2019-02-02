package poolBasedMultiThreadedDesign;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	static String folder = "ClientFiles/";
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
		  sc.close();
		  
		  long debut = System.currentTimeMillis();
			
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

			// Traitement du code de réponse
			if (reply.equals("404")) {
				System.out.println("ERROR 404: File not found");
			}
			else if (reply.equals("200")) {
				System.out.println("200: File found");
				System.out.println("Downloading...");

				is = server.getInputStream();
			  dis = new DataInputStream(is);
				length_in = dis.readInt();
				System.out.println(length_in + " bytes to download..."); // DEBUG
				b_in = new byte[length_in];
				nread = 0;
				num = 0;
				while (nread < length_in) {
					num = dis.read(b_in, nread, length_in-nread);
					if (num == -1) {
						System.out.println("Download error.");
						return;
					}
					nread += num;
				}

				FileOutputStream fstream = new FileOutputStream(new File(folder + filename));
				BufferedOutputStream out = new BufferedOutputStream(fstream);
		    out.write(b_in);
		    out.close();
		    fstream.close();
		    System.out.println("Download succesfull!");
			}
			
			System.out.print("Download time: ");
			System.out.print(System.currentTimeMillis()-debut);
			System.out.println(" ms");
			
			server.close();
			dos.close();
			dis.close();
			sc.close();
			System.out.println("End of connection with " + server.getInetAddress());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
