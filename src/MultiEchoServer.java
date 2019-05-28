import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {
	private int port;

	public MultiEchoServer(int port) {
		this.port = port;
	}

	public void startServer() {
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			// porta non disponibile
			return;
		}
		System.out.println("Server ready");
		while (true) {
			try {

				Socket socket = serverSocket.accept();
				Thread t = new Thread(new EchoServerClientHandler(socket));
				t.start();
				
				// executor.submit(new EchoServerClientHandler(socket));
			} catch (IOException e) {
				break;
				// entrerei qui se serverSocket venisse chiuso
			}
		}
		executor.shutdown();
	}

	public static void main(String[] args) {
		MultiEchoServer echoServer = new MultiEchoServer(1337);
		echoServer.startServer();
	}
}