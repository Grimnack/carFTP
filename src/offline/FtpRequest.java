package offline;

import java.io.IOException;

import online.FTPprotocol;
import online.Server;

public class FtpRequest {
	
	protected FTPprotocol ftp;
	
	public FtpRequest(FTPprotocol protocol)
	{
		this.ftp = protocol;
	}
	
	//* creer les méthodes non statiques qui ne retourne rien
	public void processRequest(String message) throws IOException
	{
		System.out.println("Process Request message : "+message);
		if(message.contains(" "))
		{
			String[] command = message.split(" ");
			switch(command[0])
			{
				case "USER":  processUSER(command[1]);
				break;
				case "PASS":  processPASS(command[1]);
				case "RETR":  processRETR(command[1]);
				case "STOR":  processSTOR(command[1]);
				case "LIST":  processLIST(command[1]);
				case "QUIT":  processQUIT(command[1]);
				case "TYPE":  processTYPE(command[1]);
			}
		}
		else
		{
			switch(message)
			{
			case "USER":  processUSER(message);
			break;
			case "PASS":  processPASS(message);
			case "RETR":  processRETR(message);
			case "STOR":  processSTOR(message);
			case "LIST":  processLIST(message);
			case "QUIT":  processQUIT(message);
			case "SYST":  processSYST(message);
			case "PWD":   processPWD(message);
			}
		}
	
	
	}

	private void processTYPE(String string) throws IOException {
		System.out.println("TYPE detected !");
		//this.ftp.write(Server.codeToMessage(200));
		//System.out.println(Server.codeToMessage(200));
		
	}

	private void processPWD(String message) throws IOException {
		System.out.println("PWD detected !");
		this.ftp.write(Server.codeToMessage(257));
		System.out.println(Server.codeToMessage(257));
	}

	private void processSYST(String message) throws IOException {
		System.out.println("SYST detected !");
		this.ftp.write(Server.codeToMessage(215));
		System.out.println(Server.codeToMessage(215));
	}

	private void processQUIT(String string) {
		
		
	}

	private void processLIST(String string) {
		// TODO Auto-generated method stub
		
	}

	private void processSTOR(String string) {
		// TODO Auto-generated method stub
		
	}

	private void processRETR(String string) {
		// TODO Auto-generated method stub
		
	}

	private void processPASS(String request) throws IOException {
		System.out.println("PASS detected");
		this.ftp.write(Server.codeToMessage(200));
		//le traitement on verra après;
		
	}

	private void processUSER(String user) throws IOException {
		System.out.println("USER detected");
		this.ftp.setUserName(user);
		if(!user.toUpperCase().equals("ANONYMOUS"))
		{
			this.ftp.write("331 : Username OK need Password\n");
		}
		
		
	}

}
