package io.checksound.networking.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Client give acknoledge they have received message.
 * 
 * @author Massimo
 *
 */
public class UDPMulticastServer2 {
	
	/**
	 * Thread di lettura degli ACKNOLEDGE inviati dai client
	 * @author Massimo
	 *
	 */
	private static class ReaderThread extends Thread {
		
		private DatagramSocket socket = null;
		private volatile boolean isToStop = false;
		
		public ReaderThread(DatagramSocket socket) {
			this.socket = socket;
		}
		
		public void doStopThread() {
			isToStop = true;
		}
		
		public void run() {
			
			while(!isToStop) {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					if(!isToStop)
						e.printStackTrace();
					else {
						// exception OK because socket closed
						break;
					}
				}
				
				String received = new String(packet.getData(), 0, packet.getLength());
				
				InetAddress inetAddress = packet.getAddress();
				int port = packet.getPort();
				System.out.println(" |---->>> RECV: " + received + 
						"| - FROM: " + inetAddress + ", on PORT: " + port);
			}
			
			System.out.println("USCITA DAL THREAD LETTURA");
		}
	}

	public static void sendUDPMessage(DatagramSocket socket, String message, String ipAddress, int port) throws IOException {
		InetAddress group = InetAddress.getByName(ipAddress);
		byte[] msg = message.getBytes();
		DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
		socket.send(packet);
	}

	private static void doSleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			
		}
	}
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		
		ReaderThread readerThread = new ReaderThread(socket);
		readerThread.start();
		
		System.out.println("Invio primo messagio");
		
		sendUDPMessage(socket, "This is a multicast messge", "230.0.0.0", 4321);
		doSleep(3);
		
		System.out.println("Invio secondo messagio");
		
		sendUDPMessage(socket, "This is the second multicast messge", "230.0.0.0", 4321);
		doSleep(3);
		
		System.out.println("Invio terzo messagio");
		
		sendUDPMessage(socket, "This is the third multicast messge", "230.0.0.0", 4321);
		doSleep(3);
		
		System.out.println("Invio OK STOP");
		sendUDPMessage(socket, "OK", "230.0.0.0", 4321);
		
		doSleep(5);
				
		readerThread.doStopThread();
				
		System.out.println("In chiusura socket");
		socket.close();
	}
}
