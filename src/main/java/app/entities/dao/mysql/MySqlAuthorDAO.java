package app.entities.dao.mysql;

import app.entities.Author;
import app.entities.dao.interfaces.AuthorDAO;
import app.util.ConfigurateManager;
import app.util.ConnectionPool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlAuthorDAO implements AuthorDAO {

    private static final String ADD;
    private static final String READ;
    private static final String READ_ALL;


    private static ConfigurateManager configurateManager;


    static{
        configurateManager = ConfigurateManager.getInstance();
        ADD = configurateManager.getProperty("mysql.author.create", "mysql");
        READ = configurateManager.getProperty("mysql.author.read", "mysql");
        READ_ALL = configurateManager.getProperty("mysql.author.readAll", "mysql");

    }
    @Override
    public boolean add(Author author) {
        CallableStatement callableStatement;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection  = null;
        try{
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(ADD);

            callableStatement.setString(1, author.getName());

            int k = callableStatement.executeUpdate();
            return k > 0;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int authorId) {
        return false;
    }

    @Override
    public boolean update(Author author, int authorId) {
        return false;
    }

    @Override
    public Author getAuthor(int authorId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ);
            callableStatement.setInt("var_id", authorId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                return new Author(authorId, name);
            }
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Collection<Author> getAllAuthors() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            connection = connectionPool.getConnection();
            callableStatement = connection.prepareCall(READ_ALL);
            ResultSet resultSet = callableStatement.executeQuery();
            Collection<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                authors.add(new Author(id, name));
            }
            return authors;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(connection);
        }
        return null;
    }
}
