package online;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	protected ServerSocket servsocket ;
	
	public Server(int port) throws IOException {
		this.servsocket = new ServerSocket(port);
	}
	
	public void launch() throws IOException {
		while(true) {
			Socket socket = this.servsocket.accept() ;
			Thread thread = new Thread(new FTPprotocol(socket)) ;
			thread.start() ;
		}
	}
	
	public static void main(String[] args){
		int port = Integer.parseInt(args[1]);
		try {
			Server server = new Server(port);
			server.launch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
