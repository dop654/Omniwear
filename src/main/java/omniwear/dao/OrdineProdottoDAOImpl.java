package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import omniwear.model.OrdineProdottoBean;

public class OrdineProdottoDAOImpl implements OrdineProdottoDAO {
	
	private static final String TABLE_NAME = "Ordine_Prodotto";
    private DataSource ds;
  

    public OrdineProdottoDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    public void doSave(OrdineProdottoBean ord) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (id_ordine, id_prodotto, quantita, prezzo_vendita) VALUES (?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
        	
        	preparedStatement.setInt(1, ord.getIdOrdine());
            preparedStatement.setInt(2, ord.getIdProdotto());
            preparedStatement.setInt(3, ord.getQuantita());
            preparedStatement.setDouble(4, ord.getPrezzo());

            preparedStatement.executeUpdate();
        }
    }

    @Override
	public Collection<OrdineProdottoBean> doRetrieveByOrdine(int id_ordine) throws SQLException {
		Collection<OrdineProdottoBean> prodottiOrdine = new LinkedList<>();
		String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_ordine = ?";

		try (Connection connection = ds.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

			preparedStatement.setInt(1, id_ordine);

			try (ResultSet rs = preparedStatement.executeQuery()) {
				while (rs.next()) {
					OrdineProdottoBean ord = new OrdineProdottoBean();
					
					ord.setIdOrdine(rs.getInt("id_ordine"));
					ord.setIdProdotto(rs.getInt("id_prodotto"));
					ord.setQuantita(rs.getInt("quantita"));
					ord.setPrezzo(rs.getFloat("prezzo_vendita"));
					
					prodottiOrdine.add(ord);
				}
			}
		}
		return prodottiOrdine;
	}
}