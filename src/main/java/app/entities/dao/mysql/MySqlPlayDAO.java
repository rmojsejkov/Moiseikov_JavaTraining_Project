package app.entities.dao.mysql;

import app.entities.Date;
import app.entities.Play;
import app.entities.dao.interfaces.PlayDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlPlayDAO implements PlayDAO {

    private static final String ADD;
    private static final String DELETE;
    private static final String UPDATE;
    private static final String READ;
    private static final String READ_ALL;

    private static ConfigurateManager configurateManager;

    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.play.create", "mysql");
        READ = configurateManager.getProperty("mysql.play.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.play.readAll", "mysql");
        DELETE = configurateManager.getProperty("mysql.play.delete", "mysql");
        UPDATE = configurateManager.getProperty("mysql.play.update", "mysql");
    }

    @Override
    public boolean add(Play play) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setString(1, play.getName());
            callableStatement.setInt(2, play.getAuthorId());
            callableStatement.setInt(3, play.getGenreId());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int playId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(DELETE);

            callableStatement.setInt("var_id", playId);

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
    public boolean update(Play play, int playId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();

            callableStatement = connection.prepareCall(UPDATE);

            callableStatement.setInt("var_id", playId);
            callableStatement.setString("var_name", play.getName());
            callableStatement.setInt("var_author_id", play.getAuthorId());
            callableStatement.setInt("var_genre_id", play.getGenreId());

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
    public Play getPlay(int playId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", playId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String  name = resultSet.getString("name");
                int authorId = resultSet.getInt("author_id");
                int genreId = resultSet.getInt("genre_id");

//                if (role) {
//                    return new Administrator(id, login, password, email, fullName);
//                } else {
//                    int birthdayYear = resultSet.getInt("birthday_year");
//                    return  new Client(id, login, password, email, fullName, birthdayYear);
//                }
                return  new Play(id, name, authorId, genreId);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Play> getAllPlay() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Play> plays = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String  name = resultSet.getString("name");
                int authorId = resultSet.getInt("author_id");
                int genreId = resultSet.getInt("genre_id");

                plays.add(new Play(id, name, authorId, genreId));
            }
            return plays;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}
