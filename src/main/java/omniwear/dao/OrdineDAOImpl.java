package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;

import omniwear.model.OrdineBean;
import omniwear.model.OrdineProdottoBean;
import omniwear.model.ProdottoBean;
import omniwear.model.UtenteBean;
import omniwear.dao.UtenteDAO;
import omniwear.model.UtenteBean;


public class OrdineDAOImpl implements OrdineDAO {

    private static final String TABLE_NAME = "Ordine";
    private DataSource ds;

    public OrdineDAOImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(OrdineBean ordine) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (indirizzo_destinazione, stato_ordine, id_utente, totale) VALUES (?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, ordine.getIndirizzoDestinazione());
            preparedStatement.setInt(2, ordine.getStatoOrdine());
            
            if (ordine.getIdUtente() != null) {
                preparedStatement.setInt(3, ordine.getIdUtente());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            
            preparedStatement.setFloat(4, ordine.getTotale());
            
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized OrdineBean doRetrieveByKey(int id_ordine) throws SQLException {
        OrdineBean ordine = null; 
        
        String selectSQL = 
            "SELECT o.*, u.nome, u.cognome, u.email " +
            "FROM " + TABLE_NAME + " o " +
            "LEFT JOIN Utente u ON o.id_utente = u.id_utente " +
            "WHERE o.id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setInt(1, id_ordine);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    ordine = new OrdineBean();
                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setDataOrdine(rs.getString("data_ordine"));
                    ordine.setIndirizzoDestinazione(rs.getString("indirizzo_destinazione"));
                    ordine.setStatoOrdine(rs.getInt("stato_ordine"));
                    ordine.setTotale(rs.getFloat("totale"));
                    
                    int idUtente = rs.getInt("id_utente");
                    if (!rs.wasNull()) {
                        ordine.setIdUtente(idUtente);
                        
                        UtenteBean utente = new UtenteBean();
                        utente.setIdUtente(idUtente);
                        utente.setNome(rs.getString("nome"));
                        utente.setCognome(rs.getString("cognome"));
                        utente.setEmail(rs.getString("email"));
                        
                        ordine.setUtente(utente);
                    }
                }
            }
        }
        return ordine;
    }

    @Override
    public synchronized Collection<OrdineBean> doRetrieveAll(String order) throws SQLException {
        Collection<OrdineBean> ordini = new LinkedList<>();
        String selectSQL = "SELECT o.*, u.nome, u.cognome, u.email " +
                "FROM " + TABLE_NAME + " o " +
                "LEFT JOIN Utente u ON o.id_utente = u.id_utente";

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                OrdineBean ordine = new OrdineBean();
                ordine.setIdOrdine(rs.getInt("id_ordine"));
                ordine.setDataOrdine(rs.getString("data_ordine"));
                ordine.setIndirizzoDestinazione(rs.getString("indirizzo_destinazione"));
                ordine.setStatoOrdine(rs.getInt("stato_ordine"));
                ordine.setTotale(rs.getFloat("totale"));
                
                int idUtente = rs.getInt("id_utente");
                if (!rs.wasNull()) {
                    ordine.setIdUtente(idUtente);
                    
                    UtenteBean utente = new UtenteBean();
                    utente.setIdUtente(idUtente);
                    utente.setNome(rs.getString("nome"));
                    utente.setCognome(rs.getString("cognome"));
                    utente.setEmail(rs.getString("email"));
                    
                    ordine.setUtente(utente);
                }
                
                ordini.add(ordine);
            }
        }
        return ordini;
    }
    
    @Override
    public synchronized Collection<OrdineBean> doRetrieveByUtente(int id_utente) throws SQLException {
        Collection<OrdineBean> ordini = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ? ORDER BY data_ordine DESC";
        
        UtenteDAO utenteDAO = new UtenteDAOImpl(ds);
        UtenteBean utenteCorrente = utenteDAO.doRetrieveByKey(id_utente);

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setInt(1, id_utente);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    OrdineBean ordine = new OrdineBean();
                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setDataOrdine(rs.getString("data_ordine"));
                    ordine.setIndirizzoDestinazione(rs.getString("indirizzo_destinazione"));
                    ordine.setStatoOrdine(rs.getInt("stato_ordine"));
                    ordine.setIdUtente(id_utente);
                    ordine.setTotale(rs.getFloat("totale"));
                    
                    ordine.setUtente(utenteCorrente);
                    
                    ordini.add(ordine);
                }
            }
        }
        return ordini;
    }
    
    @Override
    public synchronized Collection<OrdineProdottoBean> doRetrieveProdottiByOrdine(int id_ordine) throws SQLException {
        Collection<OrdineProdottoBean> lista = new LinkedList<>();
        String selectSQL = "SELECT * FROM Ordine_Prodotto WHERE id_ordine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
             
            ps.setInt(1, id_ordine);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineProdottoBean op = new OrdineProdottoBean();
                    op.setIdOrdine(rs.getInt("id_ordine"));
                    op.setIdProdotto(rs.getInt("id_prodotto"));
                    op.setQuantita(rs.getInt("quantita"));
                    op.setPrezzo(rs.getFloat("prezzo_vendita")); 
                    lista.add(op);
                }
            }
        }
        return lista;
    }
    @Override
    public synchronized boolean doUpdateStato(int id_ordine, int nuovoStato) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET stato_ordine = ? WHERE id_ordine = ?";
        
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
             
            preparedStatement.setInt(1, nuovoStato);
            preparedStatement.setInt(2, id_ordine);
            
            return (preparedStatement.executeUpdate() != 0);
        }
    }

    @Override
    public synchronized boolean doDelete(int id_ordine) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_ordine = ?";
        
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_ordine);
            
            return (preparedStatement.executeUpdate() != 0);
        }
    }
    
    public synchronized Collection<OrdineBean> doRetrieveFiltered(String email_utente, String data_ordine) throws SQLException {
        Collection<OrdineBean> ordini = new LinkedList<>();
        String selectSQL =
            "SELECT o.*, u.nome, u.cognome, u.email " +
            "FROM " + TABLE_NAME + " o " +
            "LEFT JOIN Utente u ON o.id_utente = u.id_utente WHERE 1=1";
        
        

        if (email_utente != null && !email_utente.isEmpty()) {
            selectSQL += " AND u.email LIKE ?";
        }
        if (data_ordine != null && !data_ordine.isEmpty()) {
            selectSQL += " AND o.data_ordine LIKE ?";
        }

        selectSQL += " ORDER BY o.data_ordine DESC";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL.toString())) {
            
            int parametroSQL = 1;
            if (email_utente != null) {
            	preparedStatement.setString(parametroSQL++, "%" + email_utente + "%");
            }
            if (data_ordine != null && !data_ordine.isEmpty()) {
            	preparedStatement.setString(parametroSQL++, "%" + data_ordine + "%");
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    OrdineBean ordine = new OrdineBean();
                    ordine.setIdOrdine(rs.getInt("id_ordine"));
                    ordine.setDataOrdine(rs.getString("data_ordine"));
                    ordine.setIndirizzoDestinazione(rs.getString("indirizzo_destinazione"));
                    ordine.setStatoOrdine(rs.getInt("stato_ordine"));
                    ordine.setTotale(rs.getFloat("totale"));
                    
                    int idUser = rs.getInt("id_utente");
                    if (!rs.wasNull()) {
                        ordine.setIdUtente(idUser);
                        UtenteBean utente = new UtenteBean();
                        utente.setIdUtente(idUser);
                        utente.setNome(rs.getString("nome"));
                        utente.setCognome(rs.getString("cognome"));
                        utente.setEmail(rs.getString("email"));
                        ordine.setUtente(utente);
                    }
                    ordini.add(ordine);
                }
            }
        }
        return ordini;
    }
}