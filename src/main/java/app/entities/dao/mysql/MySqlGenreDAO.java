package app.entities.dao.mysql;

import app.entities.Author;
import app.entities.Genre;
import app.entities.dao.interfaces.GenreDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlGenreDAO implements GenreDAO {

    private static final String ADD;
    private static final String READ;
    private static final String READ_ALL;


    private static ConfigurateManager configurateManager;

    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.genre.create", "mysql");
        READ = configurateManager.getProperty("mysql.genre.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.genre.readAll", "mysql");

    }

    @Override
    public boolean add(Genre genre) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setString(1, genre.getName());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int genreId) {
        return false;
    }

    @Override
    public boolean update(Genre genre, int genreId) {
        return false;
    }

    @Override
    public Genre getGenre(int genreId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", genreId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Genre(genreId, name);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Genre> getAllGenres() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                genres.add(new Genre(id, name));
            }
            return genres;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}
