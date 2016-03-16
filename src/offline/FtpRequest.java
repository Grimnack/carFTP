package offline;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import offline.Etat.StateEnum;
import online.FTPprotocol;
import online.Server;

public class FtpRequest {
	
	protected FTPprotocol ftp;
	protected ServerSocket trans;
	
	public FtpRequest(FTPprotocol protocol)
	{
		this.ftp = protocol;
	}
	
	/**
	 * Permet de traiter le message en fonction du premier mot
	 * @param message le message envoye par le client
	 * @throws IOException 
	 */
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
				case "PORT":  processPORT(command[1]);
				break;
				case "CWD" :  processCWD(command[1]);
			}
		}
		else
		{
			switch(message)
			{
			case "LIST":  processLIST(message);
			break;
			case "QUIT":  processQUIT(message);
			break;
			case "SYST":  processSYST();
			break;
			case "PWD":   processPWD(message);
			break;
			case "TYPE":  processTYPE(message);
			break;
			case "PASV": processPASV();
			break;
			default: System.out.println("default case");
			}
		}
	
	
	}

	private void processCWD(String path) throws IOException {
		this.ftp.getState().setPathname(path);
		this.ftp.write(Server.codeToMessage(200), this.ftp.getSocket());
		
	}

	private void processPASV() {
		if (this.ftp.getState().isActif() )
			this.ftp.getState().changMod() ;
		
			try {
				this.trans = new ServerSocket(0) ;
				int port = trans.getLocalPort() ;
				String local = "127,0,0,1" ;
				System.out.println("here");
				this.ftp.setPortTransfer(port);
				this.ftp.setAddr(InetAddress.getLocalHost());
				System.out.println(ftp.getAddr());
				System.out.println(ftp.getPortTransfer());
				this.ftp.write(Server.codeToMessage(227)+" ("+local+','+(port/256)+','+(port % 256)+")\n",this.ftp.getSocket() );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	private void processPORT(String args) {
		String[] argsT = args.split(",");
		int port = Integer.parseInt(argsT[4])*256+Integer.parseInt(argsT[5]);
		String ip =Integer.parseInt(argsT[0])+"."+Integer.parseInt(argsT[1])+"."+Integer.parseInt(argsT[2])+"."
				+Integer.parseInt(argsT[3]);
		InetAddress iaPort;
		try {
			this.ftp.setAddr(InetAddress.getByName(ip));
			this.trans = new ServerSocket(port);
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

	/**
	 * envoie au client le systeme du serveur
	 * @throws IOException
	 */
	private void processSYST() throws IOException {
		System.out.println("SYST detected !");
		this.ftp.write(Server.codeToMessage(215),this.ftp.getSocket());
		System.out.println(Server.codeToMessage(215));
	}

	private void processQUIT(String string) {
		
		
	}

	/**
	 * envoie au client les informations du repertoire courant du serveur
	 * @param string
	 * @throws IOException
	 */
	private void processLIST(String string) throws IOException {
		this.ftp.write(Server.codeToMessage(125),this.ftp.getSocket() );
		System.out.println(Server.codeToMessage(125));
		System.out.println("LIST detected !");
		String res ="";
		Socket socket;
		System.out.println(this.ftp.getAddr());
		socket = trans.accept();
		//socket = new Socket(this.ftp.getAddr(),this.ftp.getPortTransfer());
		File file = new File(this.ftp.getState().getCurrentPath());
		File[] files = file.listFiles();
		for(int i = 0; i<files.length; i++)
		{
			res = res+files[i].getName()+" \t";
		}
		
		this.ftp.write(res,socket);
		this.ftp.write(Server.codeToMessage(226), this.ftp.getSocket());
		System.out.println(Server.codeToMessage(226));
		socket.close();
	}

	/**
	 * is auth on enregistre sur le serveur le fichier donné
	 * @param path
	 * @throws IOException
	 */
	private void processSTOR(String path) throws IOException {
		System.out.println(path);
		if (this.ftp.getState().getState().equals(StateEnum.IDENTIFIED)){
			Socket socket;
			System.out.println("dans le processStor");
			socket = this.trans.accept();
			File file = new File(path);
			InputStream is = socket.getInputStream();
			BufferedInputStream filebuffer = new BufferedInputStream(is);
			System.out.println(file.exists());
			this.ftp.write(Server.codeToMessage(125), this.ftp.getSocket());
			FileOutputStream fos = new FileOutputStream(file) ;
			List<Byte> byteList = new ArrayList<Byte>() ;
			int nbByte = 0 ;
			int b;
			while((b=filebuffer.read())!=-1 ) {
				byteList.add(new Byte((byte)b)) ;
				nbByte ++;
			}
			filebuffer.close();
			byte[] data = new byte[nbByte];
			for (int i = 0; i < data.length; i++) {
				data[i] = byteList.get(i).byteValue() ; 
			}
			fos.write(data);	
			System.out.println("on a lu poil au");
			fos.flush();
			fos.close();
			socket.close();
			this.ftp.write(Server.codeToMessage(226), this.ftp.getSocket());
//			is.close();
		}else{
			//pas auth
		}
		
	}

	private void processRETR(String path) throws IOException {
		System.out.println(path);
		if (this.ftp.getState().getState().equals(StateEnum.IDENTIFIED)||this.ftp.getState().getState().equals(StateEnum.ANONYMOUS)){
			File fichier = new File(path);
			if (!fichier.exists()){
				this.ftp.write(Server.codeToMessage(550), this.ftp.getSocket());
			}else{
				this.ftp.write(Server.codeToMessage(125), this.ftp.getSocket());
				Socket socket = this.trans.accept();
				DataOutputStream dos= new DataOutputStream(socket.getOutputStream());
				FileInputStream fis = new FileInputStream(fichier);
				byte[] buff = new byte[socket.getSendBufferSize()];
				int lecture = fis.read(buff);
				while (lecture > 0 ){
					System.out.println(lecture);
					dos.write(buff,0,lecture);
					lecture=fis.read(buff);
				}
				fis.close();
				dos.flush();
				socket.close();
				this.ftp.write(Server.codeToMessage(226), this.ftp.getSocket());
				
				dos.close();
				}
		}
		
	}

	/**
	 * si le mot de passe est bon envoie un message ok sinon message user ou mdp incorrect
	 * @param passGiven le mot de passe envoyé par le client
	 * @throws IOException
	 */
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

	/**
	 * traitement a faire si la commande est USER
	 * @param user le nom d'user envoyé par le client
	 * @throws IOException
	 */
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
