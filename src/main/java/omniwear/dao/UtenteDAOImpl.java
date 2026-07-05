package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import omniwear.model.UtenteBean;

public class UtenteDAOImpl implements UtenteDAO{
	private static final String TABLE_NAME = "Utente";
    private DataSource ds;
    
    public UtenteDAOImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public synchronized void doSave(UtenteBean utente) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome, cognome, email, password_hash, data_nascita, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getEmail());
            preparedStatement.setString(4, utente.getPassword());            
            preparedStatement.setDate(5, java.sql.Date.valueOf(utente.getDataNascita()));
            preparedStatement.setBoolean(6, utente.getAdmin());

            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public synchronized UtenteBean doRetrieveByKey(int id_utente) throws SQLException {
        UtenteBean bean = new UtenteBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_utente = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setInt(1, id_utente);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setIdUtente(rs.getInt("id_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password_hash"));
                    bean.setAdmin(rs.getBoolean("isAdmin"));
                    
                    java.sql.Date sqlDate = rs.getDate("data_nascita");
                    if (sqlDate != null) {
                        bean.setDataNascita(sqlDate.toLocalDate());
                    }
                }
            }
        }
        return bean;
    }
    
    public synchronized UtenteBean doRetrieveByEmailPassword(String email, String password) throws SQLException{
        UtenteBean bean = new UtenteBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password_hash = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setIdUtente(rs.getInt("id_utente"));
                    bean.setNome(rs.getString("nome"));
                    bean.setCognome(rs.getString("cognome"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password_hash"));
                    bean.setAdmin(rs.getBoolean("isAdmin"));
                    
                    java.sql.Date sqlDate = rs.getDate("data_nascita");
                    if (sqlDate != null) {
                        bean.setDataNascita(sqlDate.toLocalDate());
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int id_utente) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_utente = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_utente);
            int result = preparedStatement.executeUpdate();
            return (result != 0);
        }
    }

    @Override
    public synchronized Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
        Collection<UtenteBean> utenti = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                UtenteBean bean = new UtenteBean();
                bean.setIdUtente(rs.getInt("id_utente"));
                bean.setNome(rs.getString("nome"));
                bean.setCognome(rs.getString("cognome"));
                bean.setEmail(rs.getString("email"));
                bean.setPassword(rs.getString("password_hash"));
                
                java.sql.Date sqlDate = rs.getDate("data_nascita");
                if (sqlDate != null) {
                    bean.setDataNascita(sqlDate.toLocalDate());
                }
                utenti.add(bean);
            }
        }
        return utenti;
    }
}
