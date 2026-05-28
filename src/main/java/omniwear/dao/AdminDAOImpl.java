package omniwear.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;
import omniwear.model.AdminBean;

public class AdminDAOImpl implements AdminDAO{
	private static final String TABLE_NAME = "Admin";
    private DataSource ds;
    
    public AdminDAOImpl(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public synchronized void doSave(AdminBean admin) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (id_admin, email, password_hash) VALUES (?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
             
            preparedStatement.setInt(1, admin.getIdAdmin());
            preparedStatement.setString(2, admin.getEmail());
            preparedStatement.setString(3, admin.getPassword());            
            
            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public synchronized AdminBean doRetrieveByKey(int id_admin) throws SQLException {
        AdminBean bean = new AdminBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_admin = ?";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
             
            preparedStatement.setInt(1, id_admin);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bean.setIdAdmin(rs.getInt("id_admin"));
                    bean.setEmail(rs.getString("email"));
                    bean.setPassword(rs.getString("password_hash"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int id_admin) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_admin= ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
             
            preparedStatement.setInt(1, id_admin);
            int result = preparedStatement.executeUpdate();
            return (result != 0);
        }
    }

    @Override
    public synchronized Collection<AdminBean> doRetrieveAll(String order) throws SQLException {
        Collection<AdminBean> admins = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                AdminBean bean = new AdminBean();
                bean.setIdAdmin(rs.getInt("id_admin"));
                bean.setEmail(rs.getString("email"));
                bean.setPassword(rs.getString("password_hash"));
                
               admins.add(bean);
            }
        }
        return admins;
    }
}
