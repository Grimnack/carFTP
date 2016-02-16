package online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import offline.Etat;
import offline.Etat.StateEnum;
import offline.FtpRequest;

public class FTPprotocol implements Runnable {
	protected Socket socket ;
	protected Socket portSocket;
	protected Etat etat;
	protected InputStream input;
	protected InputStreamReader inputReader;
	protected BufferedReader buff;
	protected String username = null ;
	protected FtpRequest ftpRequest;
	
	public FTPprotocol(Socket socket) {
		this.socket = socket ;
		this.etat = new Etat(".",this.socket.getPort()) ;
		this.ftpRequest = new FtpRequest(this);
	}
	
	public Etat getState() {
		return this.etat;
	}
	
	public void  setState(StateEnum state){
		this.etat.setState(state)  ;
	}
	
	public Socket getPortSocket()
	{
		return this.portSocket;
	}
	
	public void setPortStocket(Socket ps)
	{
		this.portSocket = ps;
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}

	public String read(){
		try {
			this.input = this.socket.getInputStream();
		this.inputReader = new InputStreamReader(input);
		this.buff = new BufferedReader(inputReader);
		
		String s = buff.readLine();
		//System.out.println(s);
		return s;

		} catch (IOException e) {
			return "iofail" ;
		}
		
		
	}
	
	
	public void write(String msg, Socket socket) throws IOException{
		OutputStream os = socket.getOutputStream();
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
		System.out.println(Server.codeToMessage(200));
		try {
			this.write(Server.codeToMessage(200),this.socket) ;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true) {
			
			// envoyez message avec code 200
			try {
				
				String request = this.read() ;
				System.out.println(request);
				this.ftpRequest.processRequest(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

	}

}
