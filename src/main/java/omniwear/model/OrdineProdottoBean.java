package omniwear.model;

import java.io.Serializable;

public class OrdineProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id_ordine;
    private int id_prodotto;
    private int quantita;
    private float prezzo_vendita;
    private ProdottoBean prodotto;
    
    public int getIdOrdine() {
    	return id_ordine;
    }
    public void setIdOrdine(int id) {
    	id_ordine = id;
    }
    
    public int getIdProdotto() {
    	return id_prodotto;
    }
    public void setIdProdotto(int id) {
    	id_prodotto = id;
    }
    
    public int getQuantita() {
    	return quantita;
    }
    public void setQuantita(int qt) {
    	quantita = qt;
    }
    
   public float getPrezzo() {
	   return prezzo_vendita;
   }
   public void setPrezzo(float price) {
	   prezzo_vendita = price;
   }
   
   public ProdottoBean getProdotto() {
	   return prodotto;
   }
   public void setProdotto(ProdottoBean product) {
	   prodotto = product;
   }
}
