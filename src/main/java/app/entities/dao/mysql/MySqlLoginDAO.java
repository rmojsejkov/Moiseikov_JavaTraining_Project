package app.entities.dao.mysql;

import app.entities.Login;
import app.entities.Play;
import app.entities.dao.interfaces.LoginDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlLoginDAO implements LoginDAO {

    private static final String ADD;
    private static final String READ;
    private static final String READ_ALL;
    private static final String AUTHORIZE;


    private static ConfigurateManager configurateManager;

    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.login.create", "mysql");
        READ = configurateManager.getProperty("mysql.login.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.login.readAll", "mysql");
        AUTHORIZE = configurateManager.getProperty("mysql.login.authorize", "mysql");

    }


    @Override
    public boolean add(Login login) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setString(1, login.getLogin());
            callableStatement.setString(2, login.getPassword());
            callableStatement.setString(3, login.getEmail());
            callableStatement.setInt(4, login.getRoleId());
            callableStatement.setString(5, login.getFullName());
            callableStatement.setInt(6, login.getPhone());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Login getLogin(int loginId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", loginId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phone");
                int roleId = resultSet.getInt("role_id");

//                if (role) {
//                    return new Administrator(id, login, password, email, fullName);
//                } else {
//                    int birthdayYear = resultSet.getInt("birthday_year");
//                    return  new Client(id, login, password, email, fullName, birthdayYear);
//                }
                return  new Login(id, login, password, fullName, email, phone, roleId);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Login logIn(String login, String password) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(AUTHORIZE);
            callableStatement.setString("var_login", login);
            callableStatement.setString("var_password", password);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String dbLogin = resultSet.getString("login");
                String dbPassword = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phone");
                int roleId = resultSet.getInt("role_id");


                return (new Login(id, login, password, fullName, email, phone, roleId));
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Login> getAllLogins() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Login> logins = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phone");
                int roleId = resultSet.getInt("role_id");

                logins.add(new Login(id, login, password, fullName, email, phone, roleId));
            }
            return logins;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}
