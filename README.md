# Esempi Networking

## NetworkInterface & InetAddress

Programma per visualuzzare le informazioni su le interfacce di rete sul proprio PC, utilizzando le classi `java.net.NetworkInterface` e `java.net.InetAddress`: [ShowMyNetwork](./src/ShowMyNetwork.java).


## FETCH URL

Esempio [FetchURL](./src/FetchURL.java) per connetteri a una risorsa (Es: http://www.wikipedia.it) e leggere il contenuto della pagina HTML.

```java

URL url = new URL(urlString);
URLConnection connection = url.openConnection();
InputStream urlData = connection.getInputStream();
       
/* Copy lines of text from the input stream to the screen, until end-of-file is encountered  (or an error occurs). */

BufferedReader in;  // For reading from the connection's input stream.
in = new BufferedReader( new InputStreamReader(urlData) );

while (true) {
    String line = in.readLine();
    if (line == null)
        break;
    System.out.println(line);
}
in.close();

```

## SERVIZIO DATE

Il primo esempio: [DateClient](./src/DateClient.java) e [DateServer](./src/DateServer.java). Il client fa una connessione al server, legge una riga di testo inviata dal server, e mostra il testo a console. Il testo inviato dal server consiste nella data e time corrente sul computer dove il server è attivo.

## UNA SEMPLICE NETWORK CHAT

[CLChatClient](./src/CLChatClient.java) e [CLChatServer](./src/CLChatServer.java). Nell'esempio del `DateServer`, il server trasmette l'informazione e il client legge. E' anche possibile avere una comunicazione bidirezionale tra client e server. In questo esempio il client e server possono mandarsi messaggi a vicenda. Da riga di comando gli utenti scrivono i messaggi. In questo esempio, il server aspetta una connessione da un client e poi chiude la connessione d'ascolto, così nessun altro client può connettersi. Dopo che client e server sono connessi, entrambi lavorano nello stesso modo. L'utente sul client digita un messaggio che è inviato al server, che lo mostra a display. L'utente del server allora digita un messaggio che è inviato al client, e così via.


## SERVIZIO ECHO

Client [LineClient](./src/LineClient.java) legge input da riga di comando, invia la richiesta al server su output stream e riceve la risposta dal server su input stream:

```java
public void startClient() throws IOException {
		Socket socket = new Socket(ip, port);
		System.out.println("Connection established");
		Scanner socketIn = new Scanner(socket.getInputStream());
		PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
		Scanner stdin = new Scanner(System.in);
		try {
			while (true) {
				String inputLine = stdin.nextLine();
				socketOut.println(inputLine);
				socketOut.flush();
				String socketLine = socketIn.nextLine();
				System.out.println(socketLine);
			}
		} catch (NoSuchElementException e) {
			System.out.println("Connection closed");
		} finally {
			stdin.close();
			socketIn.close();
			socketOut.close();
			socket.close();
		}
	}
```

Il server, [EchoServer](./src/EchoServer.java) in ascolto delle richieste del client dall'input a cui risponde sull'output stream:

```java
public void startServer() throws IOException {
		// apro una porta TCP
		serverSocket = new ServerSocket(port);
		System.out.println("Server	socket	ready on port:" + port);
		// resto in attesa di una connessione
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
		serverSocket.close();
    }
```

Versione del server che accetta più connessioni da più client (versione con multithead. per gestire più client contemporaneamente): [MultiEchoServer](./src/MultiEchoServer.java) e [EchoServerClientHandler](./src/EchoServerClientHandler.java).


	