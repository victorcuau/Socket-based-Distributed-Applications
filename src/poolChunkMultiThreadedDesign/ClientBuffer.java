package poolChunkMultiThreadedDesign;

import java.net.Socket;

public class ClientBuffer {

	static int tete; // Indique la position du prochain élement à récupérer (FIFO)
	static int queue; // Indique la position où écrire la prochaine fois (FIFO)
	static int nbElem; // Indique le nombre de clients actuellement dans le buffer
	
	Socket buffer[];
	
	ClientBuffer(int BufSz){
		buffer = new Socket[BufSz];
	}
	
	public synchronized void put(Socket client) throws InterruptedException {
		while(!(nbElem < buffer.length)) { // Tant que le buffer est plein
			wait();
		}
		buffer[queue] = client; // Ajout du client dans le buffer
		queue = (queue+1)%buffer.length;
		nbElem++;
		notifyAll(); 	// All car si le buffer est plein, on a absolument besoin
									// de réveiller un worker pour ne pas être bloqué
	}
	
	public synchronized Socket get() throws InterruptedException{
		while(!(nbElem>0)) { // Tant que le buffer est vide
			wait();
		}
		Socket client = buffer[tete];
		tete = (tete+1)%buffer.length;
		nbElem--;
		notifyAll();
		return client;
	}
	
}
