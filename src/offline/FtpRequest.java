package offline;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import offline.Etat.StateEnum;
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
				break;
				case "RETR":  processRETR(command[1]);
				break;
				case "STOR":  processSTOR(command[1]);
				break;
				case "LIST":  processLIST(command[1]);
				break;
				case "QUIT":  processQUIT(command[1]);
				break;
				case "PORT":  processPORT(command[1]);
				break;
			}
		}
		else
		{
			switch(message)
			{
			case "USER":  processUSER(message);
			break;
			case "PASS":  processPASS(message);
			break;
			case "RETR":  processRETR(message);
			break;
			case "STOR":  processSTOR(message);
			break;
			case "LIST":  processLIST(message);
			break;
			case "QUIT":  processQUIT(message);
			break;
			case "SYST":  processSYST(message);
			break;
			case "PWD":   processPWD(message);
			break;
			case "TYPE":  processTYPE(message);
			break;
			}
		}
	
	
	}

	private void processPORT(String args) {
		String[] argsT = args.split(",");
		int port = Integer.parseInt(argsT[4])*256+Integer.parseInt(argsT[5]);
		String ip =Integer.parseInt(argsT[0])+"."+Integer.parseInt(argsT[1])+"."+Integer.parseInt(argsT[2])+"."
				+Integer.parseInt(argsT[3]);
		InetAddress iaPort;
		try {
			iaPort = InetAddress.getByName(ip);
			Socket portSocket = new Socket(iaPort,port);
			this.ftp.setPortStocket(portSocket);
			this.ftp.write(Server.codeToMessage(200),this.ftp.getSocket());
			System.out.println(Server.codeToMessage(200));
			
		} catch (UnknownHostException e) {
			System.out.println("Adresse Incorrecte");
		} catch (IOException e) {
			System.out.println("iofail");
		}
		
	}

	private void processTYPE(String string) throws IOException {
		System.out.println("TYPE detected !");
		this.ftp.write(Server.codeToMessage(200),this.ftp.getSocket());
		System.out.println(Server.codeToMessage(200));
		
	}

	private void processPWD(String message) throws IOException {
		System.out.println("PWD detected !");
		this.ftp.write(Server.codeToMessage(257),this.ftp.getSocket());
		System.out.println(Server.codeToMessage(257));
	}

	private void processSYST(String message) throws IOException {
		System.out.println("SYST detected !");
		this.ftp.write(Server.codeToMessage(215),this.ftp.getSocket());
		System.out.println(Server.codeToMessage(215));
	}

	private void processQUIT(String string) {
		
		
	}

	private void processLIST(String string) throws IOException {
		this.ftp.write(Server.codeToMessage(125),this.ftp.getSocket() );
		System.out.println(Server.codeToMessage(125));
		System.out.println("LIST detected !");
		String res ="";
		File file = new File(this.ftp.getState().getCurrentPath());
		File[] files = file.listFiles();
		for(int i = 0; i<files.length; i++)
		{
			res = res+files[i].getName()+"\n";
		}
		this.ftp.write(res+Server.codeToMessage(226),this.ftp.getPortSocket() );
		this.ftp.write(Server.codeToMessage(200),this.ftp.getPortSocket() );
		System.out.println(Server.codeToMessage(226));
		
	}

	private void processSTOR(String string) {
		// TODO Auto-generated method stub
		
	}

	private void processRETR(String string) {
		// TODO Auto-generated method stub
		
	}

	private void processPASS(String passGiven) throws IOException {
		System.out.println("PASS detected : " + passGiven);
		String ourPass = Server.getPass(this.ftp.getUserName()) ;
		if(this.ftp.getState().getState()== StateEnum.USERGIVEN){
			if(ourPass.equals(passGiven)){
				this.ftp.setState(StateEnum.IDENTIFIED) ;
				this.ftp.write(Server.codeToMessage(230),this.ftp.getSocket()) ;
			}else{
				this.ftp.setState(StateEnum.UNIDENTIFIED) ;
				this.ftp.write(Server.codeToMessage(430),this.ftp.getSocket()) ;
			}
		}else{
			this.ftp.write(Server.codeToMessage(400),this.ftp.getSocket());
		}
		
		//le traitement on verra après;
		
	}

	private void processUSER(String user) throws IOException {
		System.out.println("USER detected");
		this.ftp.setUserName(user);
		if(!user.toUpperCase().equals("ANONYMOUS"))
		{
			this.ftp.setState(StateEnum.USERGIVEN);
			this.ftp.write("331 : Username OK need Password\n",this.ftp.getSocket());
		}else{
			this.ftp.setState(StateEnum.ANONYMOUS) ;
			this.ftp.write(Server.codeToMessage(230),this.ftp.getSocket());
		}
		
		
	}

}
