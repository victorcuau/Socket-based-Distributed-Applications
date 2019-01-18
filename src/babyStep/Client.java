package babyStep;

import java.io.*;
import java.net.*;

public class Client {
	


	
	public static void main(String args[]) {
		String serverHost = "127.0.0.1";
		int serverPort = 1234;
		Socket server;
		
		InputStream is;
		DataInputStream dis;
		int lengthIS;
		
		OutputStream os;
		DataOutputStream dos;
		int lengthOS;
		
		try {
			server = new Socket(serverHost, serverPort);
			System.out.println("Connected to " + server.getInetAddress());
			
			os = server.getOutputStream();
			dos = new DataOutputStream(os);
			String name = "Cocomo";
			byte[] bOut = name.getBytes();
			dos.writeInt(bOut.length);
			dos.write(bOut);
			System.out.println("Envoi du nom " + bOut);
			
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
			System.out.print(bIn);
			server.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
