package omniwear.model;

import java.io.Serializable;
import java.util.Collection;

public class ProdottoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id_prodotto;
	private String nome_prodotto;
	private float prezzo;
	private int id_utente;
	private UtenteBean admin;
	private Collection<CategoriaBean> categorie;
    private Collection<MisuraBean> misure;
	
	public ProdottoBean() {
		
	}
	
	public int getIdProdotto() {
		return id_prodotto;
	}
	public void setIdProdotto(int idProdotto) {
		id_prodotto = idProdotto;
	}
	
	public String getNomeProdotto() {
		return nome_prodotto;
	}
	public void setNomeProdotto(String nome) {
		nome_prodotto = nome;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	public int getIdUtente() {
		return id_utente;
	}
	public void setIdUtente(int idUtente) {
		id_utente = idUtente;
	}
	
	public UtenteBean getAdmin() {
		return admin;
	}
	public void setAdmin(UtenteBean admin) {
		this.admin = admin;
	}

	public Collection<CategoriaBean> getCategorie() { 
		return categorie; 
	}
	public void setCategorie(Collection<CategoriaBean> categorie) { 
		this.categorie = categorie; 
	}
	
	public Collection<MisuraBean> getMisure() {
		return misure; 
	}
	public void setMisure(Collection<MisuraBean> misure) { 
		this.misure = misure; 
	}
}
