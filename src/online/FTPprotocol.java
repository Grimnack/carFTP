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
	
	/**
	 * @return etat actuel du thread
	 */
	public Etat getState() {
		return this.etat;
	}
	
	/**
	 * Permet de changer l'etat du server
	 * @param state le nouvel etat
	 */
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
	
	/**
	 * en attente d'un message du client
	 * @return le message du client
	 */
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
	
	/**
	 * envoie un message au client
	 * @param msg
	 * @param socket
	 * @throws IOException
	 */
	public void write(String msg, Socket socket) throws IOException{
		OutputStream os = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.write(msg.getBytes());
	}
	
	/**
	 * @return si le client a envoyé la commande USER renvoie le username donné, sinon null
	 */
	public String getUserName() {
		return this.username ;
	}
	
	/**
	 * permet de modifier l'attribut username
	 * @param nouveau 
	 */
	public void setUserName(String nouveau) {
		this.username = nouveau ;
	}
	
	/**
	 * l'execution de FTPprotocol
	 */
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
