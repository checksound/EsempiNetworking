package io.checksound.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
	private int port;
	private ServerSocket serverSocket;

	public EchoServer(int port) {
		this.port = port;
	}

	public void startServer() throws IOException {
		// apro una porta TCP
		serverSocket = new ServerSocket(port);
		System.out.println("Server	socket	ready on port:" + port);
		// resto in attesa di una connessione

		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("Received client	connection");
			// apro gli stream di input e output per leggere
			// e scrivere nella connessione appena ricevuta
			Scanner in = new Scanner(socket.getInputStream());
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			// leggo e scrivo nella connessione finche'non
			// ricevo "quit"
			while (true) {
				String line = in.nextLine();
				if (line.equals("quit")) {
					break;
				} else {
					out.println("Received:	" + line);
					out.flush();
				}
			}
			// chiudo gli stream e il socket
			System.out.println("Closing	sockets");
			in.close();
			out.close();
			socket.close();
		}

		// serverSocket.close();
	}

	public static void main(String[] args) {
		EchoServer server = new EchoServer(1337);
		try {
			server.startServer();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}