package online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket as;
	
	public Client(String serverAddress,int port)
	{
		try {
			this.as = new Socket(serverAddress, port);
		} catch (UnknownHostException e) {
			System.out.println("Error unknown host");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O error");
			e.printStackTrace();
		} 
	}
	/**
	 * en attente d'un message du serveur
	 */
	public void read()
	{
		try
		{
			String s;
			InputStream is = this.as.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			while((s = br.readLine()) != null)
			{
				System.out.println(s);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error while reading\n"+e.getLocalizedMessage());
		}
	}
	
	/**
	 * Envoie un message au serveur
	 * @param message le message a envoyer 
	 */
	public void write(String message)
	{
		try
		{
			OutputStream os = this.as.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeBytes(message+"\n");
			
		}
		catch(Exception e)
		{
			System.out.println("Error while reading\n"+e.getLocalizedMessage());
		}
		
	}

}
