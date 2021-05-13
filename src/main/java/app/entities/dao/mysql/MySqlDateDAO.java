package app.entities.dao.mysql;

import app.entities.Date;
import app.entities.dao.interfaces.DateDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlDateDAO implements DateDAO {

    private static final String ADD;
    private static final String DELETE;
    private static final String UPDATE;
    private static final String READ;
    private static final String READ_ALL;

    private static ConfigurateManager configurateManager;

    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.date.create", "mysql");
        READ = configurateManager.getProperty("mysql.date.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.date.readAll", "mysql");
        DELETE = configurateManager.getProperty("mysql.date.delete", "mysql");
        UPDATE = configurateManager.getProperty("mysql.date.update", "mysql");

    }

    @Override
    public boolean add(Date date) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setDate(1, java.sql.Date.valueOf(date.getPlaysDate()));
            callableStatement.setInt(2, date.getPlayId());
            callableStatement.setInt(3, date.getTicketsNum());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int dateId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(DELETE);

            callableStatement.setInt("var_id", dateId);

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
    public boolean update(Date date, int dateId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(UPDATE);

            callableStatement.setInt("var_id", dateId);
            callableStatement.setDate("var_plays_date",
                    java.sql.Date.valueOf(date.getPlaysDate()));
            callableStatement.setInt("var_play_id", date.getPlayId());
            callableStatement.setInt("var_tickets_num", date.getTicketsNum());

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
    public Date getDate(int dateId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", dateId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                java.sql.Date playsDate = resultSet.getDate("plays_date");
                int playId = resultSet.getInt("play_id");
                int ticketsNum = resultSet.getInt("tickets_num");
//                if (role) {
//                    return new Administrator(id, login, password, email, fullName);
//                } else {
//                    int birthdayYear = resultSet.getInt("birthday_year");
//                    return  new Client(id, login, password, email, fullName, birthdayYear);
//                }
                return  new Date(id, playsDate.toLocalDate(), playId, ticketsNum);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Date> getAllDates() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Date> dates = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                java.sql.Date playsDate = resultSet.getDate("plays_date");
                int playId = resultSet.getInt("play_id");
                int ticketsNum = resultSet.getInt("tickets_num");

                dates.add(new Date(id, playsDate.toLocalDate(), playId, ticketsNum));
            }
            return dates;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}

