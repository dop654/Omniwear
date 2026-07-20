package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.ImmagineBean;

public interface ImmagineDAO {
    public void doSave(ImmagineBean immagine) throws SQLException;
    
    public boolean doDelete(String path_immagine) throws SQLException;
    
    public ImmagineBean doRetrieveByKey(String path_immagine) throws SQLException;
    
    public Collection<ImmagineBean> doRetrieveAll(String order) throws SQLException;

    public Collection<ImmagineBean> doRetrieveAllByProduct(int id_prodotto) throws SQLException;
}
