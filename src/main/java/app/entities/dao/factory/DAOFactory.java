package app.entities.dao.factory;

import app.entities.dao.interfaces.*;

public abstract class DAOFactory {
    private enum TypeDb{
        MYSQL
    }

    public abstract AuthorDAO getAuthorDAO();
    public abstract DateDAO getDateDAO();
    public abstract GenreDAO getGenreDAO();
    public abstract OrderDAO getOrderDAO();
    public abstract PlayDAO getPlayDAO();
    public abstract LoginDAO getLoginDAO();

    public static DAOFactory getDb() {
        return getDb("mysql");
    }

    public static DAOFactory getDb(String nameDb){
        TypeDb db = TypeDb.valueOf(nameDb.toUpperCase());
        switch (db){
            case MYSQL:
                   return new MySqlDAOFactory();
            default:
                throw new IllegalArgumentException("asdasd");
        }
    }
}
