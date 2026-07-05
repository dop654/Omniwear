package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.ProdottoBean;

public interface ProdottoDAO {
	void doSave(ProdottoBean prodotto) throws SQLException;
    
    boolean doDelete(int id_prodotto) throws SQLException;
    
    ProdottoBean doRetrieveByKey(int id_prodotto) throws SQLException;
    
    Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException;
    
    Collection<ProdottoBean> doRetrieveByNome(String nomeParziale) throws SQLException;
    
    void doSaveCategoria(int id_prodotto, String nome_categoria) throws SQLException;

    boolean doDeleteCategoria(int id_prodotto, String nome_categoria) throws SQLException;

    void doSaveMisura(int id_prodotto, String valore_misura) throws SQLException;
    
    boolean doDeleteMisura(int id_prodotto, String valore_misura) throws SQLException;
}
