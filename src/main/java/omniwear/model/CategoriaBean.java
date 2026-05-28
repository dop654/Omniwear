package omniwear.model;

import java.io.Serializable;

public class CategoriaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nome_categoria;
	
	public CategoriaBean() {
		
	}
	
	public String getNomeCat() {
		return nome_categoria;
	}
	
	public void setNomeCat(String name) {
		nome_categoria = name;
	}
}
