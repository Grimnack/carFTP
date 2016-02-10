package offline;

public class Etat {
	
	public enum StateEnum { UNIDENTIFIED ,IDENTIFIED, ANONYMOUS } ;
	protected String pathname ;
	protected boolean actif ;
	protected StateEnum state ;
	
	public Etat(String pathname){
		this.state = StateEnum.UNIDENTIFIED ;
		this.actif = true ;
		this.pathname = pathname ;
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
}