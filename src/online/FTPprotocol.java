package online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import offline.Etat;
import offline.FtpRequest;

public class FTPprotocol implements Runnable {
	protected Socket socket ;
	protected Etat etat;
	protected InputStream input;
	protected InputStreamReader inputReader;
	protected BufferedReader buff;
	protected String username = null ;
	protected FtpRequest ftpRequest;
	
	public FTPprotocol(Socket socket) {
		this.socket = socket ;
		this.etat = Etat.UNIDENTIFIED ;
		this.ftpRequest = new FtpRequest(this);
	}
	
	public Etat getState() {
		return this.etat;
	}
	
	public void  setState(Etat nouveau){
		this.etat = nouveau ;
	}

	public String read(){
		try {
			this.input = this.socket.getInputStream();
		this.inputReader = new InputStreamReader(input);
		this.buff = new BufferedReader(inputReader);
		
		String s = buff.readLine();
		System.out.println(s);
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

	public String getUserName() {
		return this.username ;
	}
	
	public void setUserName(String nouveau) {
		this.username = nouveau ;
	}
	
	public void run() {
		while(true) {
			// envoyez message avec code 200
			try {

				this.write(Server.codeToMessage(200)) ;
				String request = this.read() ;
				this.ftpRequest.processRequest(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

}
