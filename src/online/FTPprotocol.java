package online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import offline.Etat;

public class FTPprotocol implements Runnable {
	protected Socket socket ;
	protected Etat etat;
	
	public FTPprotocol(Socket socket) {
		this.socket = socket ;
		this.etat = Etat.UNIDENTIFIED ;
	}

	public String read(){
		InputStream input;
		try {
			input = this.socket.getInputStream();
		InputStreamReader inputReader = new InputStreamReader(input);
		BufferedReader buff = new BufferedReader(inputReader);
		
		String s = buff.readLine();
		return s;

		} catch (IOException e) {
			return "iofail" ;
		}
		
		
	}
	
	public void write(String msg) throws IOException{
		OutputStream os = this.socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.write(msg.getBytes());
	}
	
	public void run() {
		while(true) {
			String request = this.read() ;
			
		}

	}

}
