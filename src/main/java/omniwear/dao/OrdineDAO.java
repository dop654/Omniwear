package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.OrdineBean;

public interface OrdineDAO {
	void doSave(OrdineBean ordine) throws SQLException;
    
    boolean doDelete(int id_ordine) throws SQLException;
    
    OrdineBean doRetrieveByKey(int id_ordine) throws SQLException;
    
    Collection<OrdineBean> doRetrieveByUtente(int id_utente) throws SQLException;
    
    boolean doUpdateStato(int id_ordine, int nuovoStato) throws SQLException;
}
