package basicFileServer;

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
			
			System.out.println("Client " + client.getInetAddress() + " connected.");
			
			InputStream is = client.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			OutputStream os = client.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			
			// Réception de la requête du client
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
			String request = new String(b_in);
			System.out.println("Download request: " + request);
			
			// Traitement de la requête
			File fileRequest = new File(folder + request);
			
			if(fileRequest.isFile() && !fileRequest.isDirectory()) {
				// Le fichier existe dans le répertoire
				byte[] bOut = new byte[(int) fileRequest.length()];
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileRequest));
				bis.read(bOut, 0, bOut.length);
				dos.write(bOut, 0, bOut.length);
				dos.flush();
				bis.close();
			}
			
			else {
				// Le fichier n'existe pas dans le répertoire
				String ErrorReply = "ERROR: File not found";
				dos.writeInt(ErrorReply.length());
				dos.write(ErrorReply.getBytes());
				System.out.println(ErrorReply);
			}
			
			dis.close();
			dos.close();
			client.close();
			server.close();
		}
	}

}