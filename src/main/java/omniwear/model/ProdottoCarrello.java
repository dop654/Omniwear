package omniwear.model;

import java.io.Serializable;

public class ProdottoCarrello implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public ProdottoCarrello(ProdottoBean prod, int quantita) {
		this.prod = prod;
		this.quantita = quantita;
	}
	
	public int getId_prodotto() {
		return prod.getIdProdotto();
	}
	
	public float getPrezzo() {
		return prod.getPrezzo();
	}
	
	public int getQuantita() {
		return quantita;
	}
	
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	private ProdottoBean prod;
	private int quantita;
}
