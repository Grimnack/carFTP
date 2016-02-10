package offline;

public class Etat {
	
	public enum StateEnum { UNIDENTIFIED ,USERGIVEN ,IDENTIFIED, ANONYMOUS } ;
	protected String pathname ;
	protected boolean actif ;
	protected StateEnum state ;
	protected String username ;
	
	public Etat(String pathname){
		this.state = StateEnum.UNIDENTIFIED ;
		this.actif = true ;
		this.pathname = pathname ;
		this.username = "" ;
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
	
	public void changMod() {
		this.actif = !this.actif ;
	}
	public void setUsername(String name){
		this.username = name ;
	}
}