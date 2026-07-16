package omniwear.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrello implements Serializable{
    private static final long serialVersionUID = 1L;
    
    public Carrello() {
    	this.prodotti = new ArrayList<>();
    }
    
    public void aggiungiProdotto(ProdottoCarrello prod) {
    	for(ProdottoCarrello p : prodotti) {
    		if(p.getId_prodotto() == prod.getId_prodotto()) {
    			p.setQuantita(p.getQuantita() + prod.getQuantita());
    			return;
    		}
    	}
    	
    	prodotti.add(prod);
    }
    
    public void modificaQuantita(int id_prodotto, int quantita) {
    	for(ProdottoCarrello p : prodotti) {
    		if(p.getId_prodotto() == id_prodotto) {
    			p.setQuantita(quantita);
    			return;
    		}
    	}
    }
    
    public void rimuoviProdotto(int id_prodotto) {
    	for(int i = 0; i < prodotti.size(); i++) {
    		if(prodotti.get(i).getId_prodotto() == id_prodotto) {
    			prodotti.remove(i);
    			return;
    		}
    	}
    }
    
    public ArrayList<ProdottoCarrello> getProdotti(){
    	return prodotti;
    }
    
    public int getNProdotti() {
    	int c = 0;
    	for(ProdottoCarrello p : prodotti) {
    		c += p.getQuantita();
    	}
    	return c;
    }
    
    public float getTotale() {
    	float tot = 0;
    	for(ProdottoCarrello p : prodotti) {
    		tot += p.getPrezzo() * p.getQuantita();
    	}
    	return tot;
    }
    
    private ArrayList<ProdottoCarrello> prodotti;
}
