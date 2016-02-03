package offline;

import java.io.IOException;

import online.FTPprotocol;

public class FtpRequest {
	
	protected FTPprotocol ftp;
	
	public FtpRequest(FTPprotocol protocol)
	{
		this.ftp = protocol;
	}
	
	//* creer les méthodes non statiques qui ne retourne rien
	public void processRequest(String message)
	{
		String[] command = message.split(" ", 1);
		switch(command[0])
		{
			case "USER":  processUSER(command[1]);
			case "PASS":  processPASS(command[1]);
			case "RETR":  processRETR(command[1]);
			case "STOR":  processSTOR(command[1]);
			case "LIST":  processLIST(command[1]);
			case "QUIT":  processQUIT(command[1]);
		}
	}

	private void processQUIT(String string) {
		// TODO Auto-generated method stub
		
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

	private void processPASS(String request,String username) {
		// TODO Auto-generated method stub
		
	}

	private void processUSER(String user) throws IOException {
		this.ftp.username = user;
		this.ftp.write("331 : Username OK need Password");
		
	}

}
