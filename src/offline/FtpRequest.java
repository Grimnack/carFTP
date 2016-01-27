package offline;

public class FtpRequest {
	
	public static Object processRequest(String message)
	{
		String[] command = message.split(" ", 1);
		switch(command[0])
		{
			case "USER": return processUSER(command[1]);
			case "PASS": return processPASS(command[1]);
			case "RETR": return processRETR(command[1]);
			case "STOR": return processSTOR(command[1]);
			case "LIST": return processLIST(command[1]);
			case "QUIT": return processQUIT(command[1]);
		}
	}

	private static Object processQUIT(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object processLIST(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object processSTOR(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object processRETR(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object processPASS(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object processUSER(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
