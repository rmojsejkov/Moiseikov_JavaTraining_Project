package app.entities.dao.factory;

import app.entities.dao.interfaces.*;
import app.entities.dao.mysql.*;

public class MySqlDAOFactory extends DAOFactory {
    @Override
    public AuthorDAO getAuthorDAO() {
        return new MySqlAuthorDAO();
    }

    @Override
    public DateDAO getDateDAO() {
        return new MySqlDateDAO();
    }

    @Override
    public GenreDAO getGenreDAO() {
        return new MySqlGenreDAO();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new MySqlOrderDAO();
    }

    @Override
    public PlayDAO getPlayDAO() {
        return new MySqlPlayDAO();
    }

    @Override
    public LoginDAO getLoginDAO() {
        return new MySqlLoginDAO();
    }
}
