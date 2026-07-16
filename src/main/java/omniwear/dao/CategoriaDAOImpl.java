package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import omniwear.model.CategoriaBean;

public class CategoriaDAOImpl implements CategoriaDAO{
	private static final String TABLE_NAME = "Categoria";
    private DataSource ds;
    
    public CategoriaDAOImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public synchronized void doSave(CategoriaBean categoria) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (nome_categoria) VALUES (?)";

        try (Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, categoria.getNomeCat());         
            
            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public synchronized CategoriaBean doRetrieveByKey(String nome_categoria) throws SQLException {
        	CategoriaBean bean = new CategoriaBean();
        	String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_categoria = ?";

        try (Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setString(1, nome_categoria);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setNomeCat(rs.getString("nome_categoria"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(String nome_categoria) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome_categoria = ?";
        try (Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setString(1, nome_categoria);
            int result = preparedStatement.executeUpdate();
            return (result != 0);
        }
    }

    @Override
    public synchronized Collection<CategoriaBean> doRetrieveAll(String order) throws SQLException {
        Collection<CategoriaBean> categorie = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                CategoriaBean bean = new CategoriaBean();
                bean.setNomeCat(rs.getString("nome_categoria"));
                
                categorie.add(bean);
            }
        }
        return categorie;
    }
    
    public synchronized Collection<CategoriaBean> doRetrieveProductCategories(int id_prodotto) throws SQLException {
        Collection<CategoriaBean> categorie = new LinkedList<>();
        String selectSQL = "SELECT * FROM prodotto_categoria WHERE id_prodotto = ?";

        try (Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)){
        	preparedStatement.setInt(1, id_prodotto);
    		ResultSet rs = preparedStatement.executeQuery();
    		
    		while (rs.next()) {
    			CategoriaBean bean = new CategoriaBean();
    			bean.setNomeCat(rs.getString("nome_categoria"));     
    			categorie.add(bean);
            }
        }
        return categorie;
    }
}
