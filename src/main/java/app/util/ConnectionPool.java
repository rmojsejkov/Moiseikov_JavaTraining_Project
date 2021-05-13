package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private static final int POOL_SIZE;
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;

    /**
     * Read parameters for connecting to database.
     */
    static {
        ConfigurateManager configurateManager = ConfigurateManager.getInstance();
        URL = configurateManager.getProperty("db.url", "config");
        USER = configurateManager.getProperty("db.user", "config");
        PASSWORD = configurateManager.getProperty("db.password", "config");
        POOL_SIZE = Integer.parseInt(configurateManager.getProperty("db.poolSize", "config"));
    }

    /**
     * Return instance "ConnectionPool". If instance equally null. Create new instance.
     *
     * @return ConnectionPool
     */
    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * Create "POOL_SIZE" "Connecting" and added in ArrayBlockingQueue
     */
    private ConnectionPool(){
        connections = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++){
            Connection connection = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,  USER, PASSWORD);
                connections.offer(connection);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return "Connection" from queue.
     *
     * @return
     * @throws InterruptedException
     */
    public Connection getConnection() throws InterruptedException {
        return connections.take();
    }

    /**
     * "Connection" comes back in queue.
     *
     * @param connection
     */
    public void closeConnection(Connection connection) {
        if (connection != null){
            connections.offer(connection);
        }
    }
}
