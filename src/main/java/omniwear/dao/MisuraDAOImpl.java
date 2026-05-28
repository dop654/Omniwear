package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import omniwear.model.MisuraBean;

public class MisuraDAOImpl implements MisuraDAO{
	private static final String TABLE_NAME = "Misura";
    private DataSource ds;
    
    public MisuraDAOImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public synchronized void doSave(MisuraBean misura) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (valore_misura) VALUES (?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setString(1, misura.getMisura());         
            
            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public synchronized MisuraBean doRetrieveByKey(String valore_misura) throws SQLException {
        	MisuraBean bean = new MisuraBean();
        	String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE valore_misura = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setString(1, valore_misura);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setMisura(rs.getString("valore_misura"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(String valore_misura) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE valore_misura = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setString(1, valore_misura);
            int result = preparedStatement.executeUpdate();
            return (result != 0);
        }
    }

    @Override
    public synchronized Collection<MisuraBean> doRetrieveAll(String order) throws SQLException {
        Collection<MisuraBean> misure = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                MisuraBean bean = new MisuraBean();
                bean.setMisura(rs.getString("valore_misura"));
                
                misure.add(bean);
            }
        }
        return misure;
    }
}
