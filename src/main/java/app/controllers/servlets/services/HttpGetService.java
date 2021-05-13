package app.controllers.servlets.services;

import app.controllers.servlets.UtilFactory;
import app.data.converters.LoginJsonConverter;
import app.data.converters.PlayJsonConverter;
import app.entities.Login;
import app.entities.Play;
import app.entities.dao.factory.DAOFactory;
import app.entities.dao.interfaces.PlayDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

public final class HttpGetService {
    private static final Gson GSON = UtilFactory.getGSON();

    private HttpGetService() {
    }

    public static String getAuth(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Login login = (Login) session.getAttribute("authUser");
        if (login == null) {
            throw new NullPointerException("Account not auth");
        }
        return GSON.toJson(login, Login.class);
    }

    public static String getAllPlays() {
        PlayDAO playDAO = DAOFactory.getDb().getPlayDAO();
        Collection<Play> plays = playDAO.getAllPlay();
        return GSON.toJson(plays);
    }

    public static String getPlay(int id) {
        PlayDAO playDAO = DAOFactory.getDb().getPlayDAO();
        Play play = playDAO.getPlay(id);
        return GSON.toJson(play);
    }

    public static String getAuthUser(HttpServletRequest request) {
        Login login = (Login) request.getSession().getAttribute("authUser");
        if (login != null) {
            return GSON.toJson(login);
        } else {
            throw new NullPointerException("user not found");
        }
    }
}
