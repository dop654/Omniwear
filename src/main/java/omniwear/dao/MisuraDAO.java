package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;

import omniwear.model.MisuraBean;

public interface MisuraDAO {
	 	public void doSave(MisuraBean misura) throws SQLException;
	    
	    public boolean doDelete(String valore_misura) throws SQLException;
	    
	    public MisuraBean doRetrieveByKey(String valore_misura) throws SQLException;
	    
	    public Collection<MisuraBean> doRetrieveAll(String order) throws SQLException;
}
