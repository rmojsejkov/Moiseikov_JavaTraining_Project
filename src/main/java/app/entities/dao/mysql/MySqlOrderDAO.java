package app.entities.dao.mysql;

import app.entities.Order;
import app.entities.Play;
import app.entities.dao.interfaces.OrderDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlOrderDAO implements OrderDAO {

    private static final String ADD;
    private static final String DELETE;
    private static final String UPDATE;
    private static final String READ;
    private static final String READ_ALL;

    private static ConfigurateManager configurateManager;

    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.order.create", "mysql");
        READ = configurateManager.getProperty("mysql.order.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.order.readAll", "mysql");
        DELETE = configurateManager.getProperty("mysql.order.delete", "mysql");
        UPDATE = configurateManager.getProperty("mysql.order.update", "mysql");
    }

    @Override
    public boolean add(Order order) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setInt(1, order.getRowId());
            callableStatement.setInt(2, order.getLoginId());
            callableStatement.setInt(3, order.getDateId());
            callableStatement.setString(4, order.getCategory());
            callableStatement.setFloat(5, order.getPrice());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int orderId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(DELETE);

            callableStatement.setInt("var_id", orderId);

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Order order, int orderId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(UPDATE);

            callableStatement.setInt("var_row_id", orderId);
            callableStatement.setInt("var_login_id", order.getLoginId());
            callableStatement.setInt("var_date_id", order.getDateId());
            callableStatement.setString("var_category", order.getCategory());
            callableStatement.setFloat("var_price", order.getPrice());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Order getOrder(int orderId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", orderId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int  rowId = resultSet.getInt("row_id");
                int loginId = resultSet.getInt("login_id");
                int dateId = resultSet.getInt("date_id");
                String category = resultSet.getString("category");
                Float price = resultSet.getFloat("price");

//                if (role) {
//                    return new Administrator(id, login, password, email, fullName);
//                } else {
//                    int birthdayYear = resultSet.getInt("birthday_year");
//                    return  new Client(id, login, password, email, fullName, birthdayYear);
//                }
                return  new Order(id, rowId, loginId, dateId, category, price);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Order> getAllOrders() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int  rowId = resultSet.getInt("row_id");
                int loginId = resultSet.getInt("login_id");
                int dateId = resultSet.getInt("date_id");
                String category = resultSet.getString("category");
                Float price = resultSet.getFloat("price");

                orders.add(new Order(id, rowId, loginId, dateId, category, price));
            }
            return orders;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}
