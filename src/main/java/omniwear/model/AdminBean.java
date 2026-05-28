package omniwear.model;

import java.io.Serializable;

public class AdminBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id_admin;
	private String email;
	private String password_hash;
	
	public AdminBean() {
		
	}
	
	public int getIdAdmin() {
		return id_admin;
	}
	public void setIdAdmin(int id) {
		id_admin = id;
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
	

}
