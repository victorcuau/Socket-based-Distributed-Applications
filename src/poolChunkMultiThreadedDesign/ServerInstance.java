package poolChunkMultiThreadedDesign;

import java.io.*;
import java.net.*;

public class ServerInstance extends Thread {
	
	static int chunk = 512; // Taille des chunks
	int id; // Identification unique du thread, pour debug notamment
	int port;
	String folder;
	ClientBuffer buffer;
	
	ServerInstance(int id, int port, String folder, ClientBuffer buffer){
		this.id = id;
		this.port = port;
		this.folder = folder;
		this.buffer = buffer;
	}

	public void run() {
		while(true) {
			try {
				// Le serveur est en attente de connexion
				Socket client = buffer.get();
				System.out.println("Client " + client.getInetAddress() + " connected to thread " + id + ".");
				// Un client est connecté
				
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
					if (num < 0) {
						return;
					}
					nread += num;
				}
				String request = new String(b_in);
				System.out.println("-------- THREAD " + id + " --------");
				System.out.println("Download request: " + request);
				
				// Traitement de la requête
				File fileRequest = new File(folder + request);
				
				if(fileRequest.isFile() && !fileRequest.isDirectory()) {
					// Le fichier existe dans le répertoire
					String ErrorCode = "200";
					dos.writeInt(ErrorCode.length());
					dos.write(ErrorCode.getBytes());
					System.out.println("200: File found");
					System.out.println("Uploading...");
	
					byte[] bOut = new byte[chunk];
					FileInputStream fis = new FileInputStream(fileRequest);
		      dos.writeInt((int) fileRequest.length());
		      System.out.println(fileRequest.length() + " bytes to upload...");
		      while(fis.read(bOut) >= 0) {
		      	dos.write(bOut);
		      }
//		      int off = 0;
//		      int next = 0;
//		      while((next = fis.read(bOut, off, Math.min(512, (int)fileRequest.length() - off))) >= 0) {
//		      	dos.write(bOut);
//		      	off+=next;
//		      }
		      System.out.println("Upload succesfull!");
					dos.flush();
					fis.close();
				}
				
				else {
					// Le fichier n'existe pas dans le répertoire
					String ErrorCode = "404";
					os = client.getOutputStream();
					dos = new DataOutputStream(os);
					dos.writeInt(ErrorCode.length());
					dos.write(ErrorCode.getBytes());
					System.out.println("ERROR 404: File not found");
				}
				
				dis.close();
				dos.close();
				client.close();
				System.out.println("Client " + client.getInetAddress() + " disconnected.");
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
