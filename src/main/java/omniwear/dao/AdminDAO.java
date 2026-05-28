package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.AdminBean;

public interface AdminDAO {
    public void doSave(AdminBean admin) throws SQLException;
    
    public boolean doDelete(int id_admin) throws SQLException;
    
    public AdminBean doRetrieveByKey(int id_admin) throws SQLException;
    
    public Collection<AdminBean> doRetrieveAll(String order) throws SQLException;

}
