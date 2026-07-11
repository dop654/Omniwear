package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.UtenteBean;

public interface UtenteDAO {
    public void doSave(UtenteBean utente) throws SQLException;
    
    public boolean doDelete(int id_utente) throws SQLException;
    
    public void doUpdate(UtenteBean newUtente) throws SQLException;
    
    public UtenteBean doRetrieveByKey(int id_utente) throws SQLException;
    
    public UtenteBean doRetrieveByEmailPassword(String email, String password) throws SQLException;
    
    public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException;
}
