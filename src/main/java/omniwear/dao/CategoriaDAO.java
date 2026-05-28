package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;

import omniwear.model.CategoriaBean;

public interface CategoriaDAO {
	 	public void doSave(CategoriaBean categoria) throws SQLException;
	    
	    public boolean doDelete(String nome_categoria) throws SQLException;
	    
	    public CategoriaBean doRetrieveByKey(String nome_categoria) throws SQLException;
	    
	    public Collection<CategoriaBean> doRetrieveAll(String order) throws SQLException;
}
