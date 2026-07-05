package omniwear.model;

import java.io.Serializable;

public class ImmagineBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String path_immagine;
	private int id_prodotto;
	
	public ImmagineBean() {
		
	}
	
	public String getPath() {
		return this.path_immagine;
	}
	public void setPath(String path) {
		this.path_immagine = path;
	}
	public int getIdProdotto() {
		return this.id_prodotto;
	}
	public void setIdProdotto(int id_prodotto) {
		this.id_prodotto = id_prodotto;
	}
	
	
}
