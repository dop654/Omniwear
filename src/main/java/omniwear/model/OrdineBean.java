package omniwear.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrdineBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id_ordine;
	private String data_ordine;
	private String indirizzo_destinazione;
	private int stato_ordine;
	private Integer id_utente;
	private UtenteBean utente;
	private float totale;
	
	public OrdineBean() {
	
	}
	
	public int getIdOrdine() {
		return id_ordine;
	}
	public void setIdOrdine(int idOrdine) {
		id_ordine = idOrdine;
	}
	
	public String getDataOrdine() {
		return data_ordine;
	}
	public void setDataOrdine(String data) {
		data_ordine = data;
	}
	
	public String getIndirizzoDestinazione() {
		return indirizzo_destinazione;
	}
	public void setIndirizzoDestinazione(String indirizzo) {
		indirizzo_destinazione = indirizzo;
	}
	
	public int getStatoOrdine() {
		return stato_ordine;
	}
	public void setStatoOrdine(int stato) {
		stato_ordine = stato;
	}
	
	public Integer getIdUtente() {
		return id_utente;
	}
	public void setIdUtente(Integer id) {
		id_utente = id;
	}
	
	public UtenteBean getUtente() {
		return utente;
	}
	public void setUtente(UtenteBean utente) {
		this.utente = utente;
	}
	
	public float getTotale() {
	    return totale;
	}

	public void setTotale(float totale) {
	    this.totale = totale;
	}
}
