package app.entities.dao.interfaces;

import app.entities.Login;

import java.util.Collection;

public interface LoginDAO {
    boolean add(Login login);
    Login getLogin(int loginId);
    Login logIn(String login, String password);
    Collection<Login> getAllLogins();
}
