package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import omniwear.model.ImmagineBean;

public class ImmagineDAOImpl implements ImmagineDAO{
	private static final String TABLE_NAME = "Immagine";
    private DataSource ds;
    
    public ImmagineDAOImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public synchronized void doSave(ImmagineBean immagine) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (path_immagine, id_prodotto) VALUES (?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, immagine.getPath());
            preparedStatement.setInt(2, immagine.getIdProdotto());

            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public synchronized ImmagineBean doRetrieveByKey(String path_immagine) throws SQLException {
        ImmagineBean bean = new ImmagineBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE path_immagine = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setString(1, path_immagine);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setPath(rs.getString("path_immagine"));
                    bean.setIdProdotto(rs.getInt("id_prodotto"));
                  
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(String path_immagine) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE path_immagine = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setString(1, path_immagine);
            int result = preparedStatement.executeUpdate();
            return (result != 0);
        }
    }

    @Override
    public synchronized Collection<ImmagineBean> doRetrieveAll(String order) throws SQLException {
        Collection<ImmagineBean> immagini = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                ImmagineBean bean = new ImmagineBean();
                bean.setPath(rs.getString("path_immagine"));
                bean.setIdProdotto(rs.getInt("id_prodotto"));
                
                
                immagini.add(bean);
            }
        }
        return immagini;
    }
}
