package mainPrograms;
import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server implements Runnable {
	
	private static int portNum = 5005;
	private ServerSocket serverSocket;
	public int uniqueId;
	private ConcurrentLinkedQueue<ClientThread> list;
	
	public Server () { }
	
	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void run() {
		list = new ConcurrentLinkedQueue<ClientThread>();
		try {
			serverSocket = new ServerSocket(portNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started");
		handleConnection();
	}
	
	public void handleConnection () {
		while(true) {  // Handle an infinite number of connections.
			try {
				Socket clientSocket = serverSocket.accept();
				ClientThread t = new ClientThread(clientSocket);
				list.add(t);
				t.start();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	// Utilized this idea of a "ClientThread" from here: http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
	class ClientThread extends Thread {
		
		Socket socket;
		ObjectOutputStream out;
		ObjectInputStream in;
		int clientId;
		
		ClientThread (Socket socket) {
			this.clientId = uniqueId;
			this.socket = socket;
			uniqueId++;
		}
		
		public int getClientId() {
			return clientId;
		}

		public void setClientId(int clientId) {
			this.clientId = clientId;
		}

		public void run () {
			try {
				out = new ObjectOutputStream(this.socket.getOutputStream());
				in = new ObjectInputStream(this.socket.getInputStream());
				MainProgram.addToOutput(out);
				MainProgram.addToInput(in);
				MainProgram.startGame(clientId);
				synchronized(this) {
					notify();
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
}
