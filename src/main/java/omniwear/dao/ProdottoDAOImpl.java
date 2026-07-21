package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;

import omniwear.model.ProdottoBean;
import omniwear.model.UtenteBean;

public class ProdottoDAOImpl implements ProdottoDAO {

    private static final String TABLE_NAME = "Prodotto";
    private DataSource ds;

    public ProdottoDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(ProdottoBean prodotto) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome_prodotto, prezzo, quantita, id_utente) VALUES (?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, prodotto.getNomeProdotto());
            preparedStatement.setFloat(2, prodotto.getPrezzo());
            preparedStatement.setInt(3, prodotto.getQt()); 
            preparedStatement.setInt(4, prodotto.getIdUtente());

            preparedStatement.executeUpdate();
        }
    }
    
    public synchronized void doUpdate(ProdottoBean prodotto) throws SQLException {
    	String updateSQL = "UPDATE Prodotto SET nome_prodotto = ?, prezzo = ?, quantita = ? WHERE id_prodotto = ?";
    	
    	try (Connection connection = ds.getConnection();
    		PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
    		
    		preparedStatement.setString(1, prodotto.getNomeProdotto());
    		preparedStatement.setFloat(2, prodotto.getPrezzo());
    		preparedStatement.setInt(3, prodotto.getQt()); 
    		preparedStatement.setInt(4, prodotto.getIdProdotto());
    		
    		preparedStatement.executeUpdate();
    	}
    }
    
    @Override
    public synchronized ProdottoBean doRetrieveByKey(int id_prodotto) throws SQLException {
        ProdottoBean prodotto = null;
        
        String selectSQL = 
            "SELECT p.*, u.nome, u.cognome, u.email " +
            "FROM " + TABLE_NAME + " p " +
            "JOIN Utente u ON p.id_utente = u.id_utente " +
            "WHERE p.id_prodotto = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    prodotto = new ProdottoBean();
                    prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                    prodotto.setNomeProdotto(rs.getString("nome_prodotto"));
                    prodotto.setPrezzo(rs.getFloat("prezzo"));
                    prodotto.setQt(rs.getInt("quantita")); 
                    prodotto.setIdUtente(rs.getInt("id_utente"));
                    
                    UtenteBean admin = new UtenteBean();
                    admin.setIdUtente(rs.getInt("id_utente"));
                    admin.setNome(rs.getString("nome"));
                    admin.setCognome(rs.getString("cognome"));
                    admin.setEmail(rs.getString("email"));
                    
                    prodotto.setAdmin(admin);
                }
            }
        }
        return prodotto;
    }

    @Override
    public synchronized boolean doDelete(int id_prodotto) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_prodotto = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);
            return (preparedStatement.executeUpdate() != 0);
        }
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        Collection<ProdottoBean> prodotti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                ProdottoBean prodotto = new ProdottoBean();
                prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                prodotto.setNomeProdotto(rs.getString("nome_prodotto"));
                prodotto.setPrezzo(rs.getFloat("prezzo"));
                prodotto.setQt(rs.getInt("quantita")); 
                prodotto.setIdUtente(rs.getInt("id_utente"));
                
                prodotti.add(prodotto);
            }	
        }
        return prodotti;
    }

    @Override
    public synchronized Collection<ProdottoBean> doRetrieveByNome(String nomeParziale) throws SQLException {
        Collection<ProdottoBean> prodotti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_prodotto LIKE ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setString(1, "%" + nomeParziale + "%");

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                    prodotto.setNomeProdotto(rs.getString("nome_prodotto"));
                    prodotto.setPrezzo(rs.getFloat("prezzo"));
                    prodotto.setQt(rs.getInt("quantita")); 
                    prodotto.setIdUtente(rs.getInt("id_utente"));
                    
                    prodotti.add(prodotto);
                }
            }
        }
        return prodotti;
    }
    
    @Override
    public synchronized Collection<ProdottoBean> doRetrieveByCategoria(String[] categorie) throws SQLException {
        Collection<ProdottoBean> prodotti = new LinkedList<>();
        
        String selectSQL = 
                "SELECT p.* " +
                "FROM " + TABLE_NAME + " p " +
                "JOIN Prodotto_Categoria pc ON p.id_prodotto = pc.id_prodotto " +
                "WHERE pc.nome_categoria = ?";
        
        for(int i = 1; i<categorie.length; i++) {
        	selectSQL += " AND pc.nome_categoria = ?";
        }

        
        try (Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            int i = 1;
        	
        	for(String c : categorie) {
        		preparedStatement.setString(i, c);
        		i++;
        	}

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ProdottoBean prodotto = new ProdottoBean();
                    prodotto.setIdProdotto(rs.getInt("id_prodotto"));
                    prodotto.setNomeProdotto(rs.getString("nome_prodotto"));
                    prodotto.setPrezzo(rs.getFloat("prezzo"));
                    prodotto.setQt(rs.getInt("quantita"));
                    prodotto.setIdUtente(rs.getInt("id_utente"));
                    
                    prodotti.add(prodotto);
                }
            }
        }
        return prodotti;
    }
    
    @Override
    public synchronized void doSaveCategoria(int id_prodotto, String nome_categoria) throws SQLException {
        String insertSQL = "INSERT INTO Prodotto_Categoria (id_prodotto, nome_categoria) VALUES (?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);
            preparedStatement.setString(2, nome_categoria);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized boolean doDeleteCategoria(int id_prodotto, String nome_categoria) throws SQLException {
        String deleteSQL = "DELETE FROM Prodotto_Categoria WHERE id_prodotto = ? AND nome_categoria = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);
            preparedStatement.setString(2, nome_categoria);

            return (preparedStatement.executeUpdate() != 0);
        }
    }
    
    @Override
    public synchronized void doSaveMisura(int id_prodotto, String valore_misura) throws SQLException {
        String insertSQL = "INSERT INTO Prodotto_Misura (id_prodotto, valore_misura) VALUES (?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);
            preparedStatement.setString(2, valore_misura);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized boolean doDeleteMisura(int id_prodotto, String valore_misura) throws SQLException {
        String deleteSQL = "DELETE FROM Prodotto_Misura WHERE id_prodotto = ? AND valore_misura = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_prodotto);
            preparedStatement.setString(2, valore_misura);

            return (preparedStatement.executeUpdate() != 0);
        }
    }

}