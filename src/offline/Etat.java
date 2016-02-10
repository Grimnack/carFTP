package offline;

public class Etat {
	
	public enum StateEnum { UNIDENTIFIED ,USERGIVEN ,IDENTIFIED, ANONYMOUS } ;
	protected String pathname ;
	protected boolean actif ;
	protected StateEnum state ;
	protected String username ;
	protected boolean newPort ;
	protected String portAddresse ;
	protected int port ;
	
	public Etat(String pathname,int port){
		this.state = StateEnum.UNIDENTIFIED ;
		this.actif = true ;
		this.pathname = pathname ;
		this.username = "" ;
		this.port = port ;
		this.newPort = false ;
	}
	
	public String getUserName() {
		return this.username;
	}
	
	public StateEnum getState() {
		return this.state ;
	}
	
	public boolean isActif() {
		return this.actif ;
	}
	
	public String getCurrentPath() {
		return this.pathname ;
	}
	
	public void setPathname(String path) {
		this.pathname = path ;
	}
	
	public void setState(StateEnum state) {
		this.state = state ;
	}
	
	public void setPort(int port){
		this.port = port ;
	}
	
	public void setAddresse(String addresse) {
		this.portAddresse = addresse ;
	}
	
	public void changMod() {
		this.actif = !this.actif ;
	}
	public void setUsername(String name){
		this.username = name ;
	}
}