package omniwear.model;

import java.io.Serializable;

public class MisuraBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String valore_misura;
	
	public MisuraBean() {
		
	}
	
	public String getMisura() {
		return valore_misura;
	}
	
	public void setMisura(String misura) {
		valore_misura = misura;
	}
}
