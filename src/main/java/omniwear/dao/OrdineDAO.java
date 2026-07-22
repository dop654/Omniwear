package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import omniwear.model.OrdineBean;
import omniwear.model.OrdineProdottoBean;
import omniwear.model.ProdottoBean;

public interface OrdineDAO {
	void doSave(OrdineBean ordine) throws SQLException;
    
    boolean doDelete(int id_ordine) throws SQLException;
    
    OrdineBean doRetrieveByKey(int id_ordine) throws SQLException;
    
    Collection<OrdineBean> doRetrieveAll(String order) throws SQLException;
    
    Collection<OrdineBean> doRetrieveByUtente(int id_utente) throws SQLException;
    
    Collection<OrdineProdottoBean> doRetrieveProdottiByOrdine(int id_ordine) throws SQLException; 
    
    boolean doUpdateStato(int id_ordine, int nuovoStato) throws SQLException;
    
    Collection<OrdineBean> doRetrieveFiltered(String email_utente, String data_ordine) throws SQLException;
}
