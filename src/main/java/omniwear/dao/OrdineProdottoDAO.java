package omniwear.dao;

import java.sql.SQLException;
import java.util.Collection;
import omniwear.model.OrdineProdottoBean;

public interface OrdineProdottoDAO {

	void doSave(OrdineProdottoBean ordineProdotto) throws SQLException;
	
	Collection<OrdineProdottoBean> doRetrieveByOrdine(int idOrdine) throws SQLException;
}
