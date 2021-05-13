package app.controllers.servlets.services;

import app.controllers.servlets.UtilFactory;
import app.entities.Login;
import app.entities.dao.factory.DAOFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class HttpPostService {

    private static final Gson GSON = UtilFactory.getGSON();

    private HttpPostService() {
    }

    public static String logIn(HttpServletRequest request) {
        JsonObject jsonObject = UtilFactory.convertHttpRequestToJsonObject(request);
        HttpSession session = request.getSession();

        String login = jsonObject.get("login").getAsString();
        String password = jsonObject.get("password").getAsString();

        Login user = DAOFactory.getDb().getLoginDAO()
                .logIn(login, password);
        if (user == null) {
            throw new NullPointerException("Wrong password");
        }
        session.setAttribute("authUser", user);
        return GSON.toJson(user);
    }

    public static String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("authUser");
        JsonObject result = new JsonObject();
        result.addProperty("status", "successful");
        return result.toString();
    }
}
