package omniwear.model;

import java.io.Serializable;
import java.time.LocalDate;

public class UtenteBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id_utente;
	private String nome;
	private String cognome;
	private String email;
	private String password_hash;
	private String data_nascita;
	private boolean isAdmin;
	
	public UtenteBean() {
		
	}
	
	public int getIdUtente() {
		return id_utente;
	}
	public void setIdUtente(int id) {
		id_utente = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String name) {
		nome=name;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String surname) {
		cognome = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password_hash;
	}
	public void setPassword(String password) {
		password_hash = password;
	}
	public String getDataNascita() {
		return data_nascita;
	}
	public void setDataNascita(String data) {
		data_nascita = data;
	}
	public boolean getAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
	
	
}
