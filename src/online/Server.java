package online;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	protected static ServerSocket servsocket ;
	protected static ConcurrentHashMap<Integer, String> reponses = new ConcurrentHashMap<Integer,String>() ;
	protected static ConcurrentHashMap<String,String> lesComptes = new ConcurrentHashMap<String, String>();
	
	public Server(int port) throws IOException {
		Server.servsocket = new ServerSocket(port);
		reponses.put(200, "200 OK\n");
		reponses.put(211, "211 End\n");
		reponses.put(215, "215 UNIX\n");
		reponses.put(230, "230 AUTH OK\n");
		reponses.put(234, "234 AUTH command OK. Initializing SSL connection.\n");
		reponses.put(257, "257 /home \n");//test
		reponses.put(331, "331 UserName OK. waiting for password \n");
		reponses.put(400, "400 Command not accepted, try again\n");
		reponses.put(421, "421 Service not available\n");
		reponses.put(425, "425 Can't open data connection\n");
		reponses.put(430, "430 Invalid username or password\n");
		reponses.put(500, "500 Syntax error, command unrecognized\n");
		reponses.put(502, "502 Command not implemented\n");
		reponses.put(530, "530 Not logged in\n");
		reponses.put(532, "532 Need account for storing files\n");
		lesComptes.put("matthieu", "caron") ;
		lesComptes.put("superman", "notabird");
	}
	
	public static String codeToMessage(int code){
		return Server.reponses.get(code) ;
	}
	
	public static String getPass(String username){
		return Server.lesComptes.get(username);
	}
	
	
	public void launch() throws IOException {
		while(true) {
			Socket socket = Server.servsocket.accept() ;
			Thread thread = new Thread(new FTPprotocol(socket)) ;
			thread.start() ;
		}
	}
	
	public static void main(String[] args){
		int port = Integer.parseInt(args[0]);
		try {
			Server server = new Server(port);
			server.launch();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
