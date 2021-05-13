package app.controllers.servlets;


import app.controllers.servlets.services.HttpGetService;
import app.controllers.servlets.services.HttpPostService;
import app.entities.Login;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/data-api/*")
public class DataAPIServlet extends HttpServlet {

    private static final JsonObject ERROR_MSG = getErrorMsg();

    private static JsonObject getErrorMsg() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("msg", "Something went wrong");

        return jsonObject;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] urlsPath = request.getPathInfo().split("/");
        String urlPath = urlsPath[1];
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            switch (urlPath) {
                case "login":
                    response.getWriter().write(HttpPostService.logIn(request));
                    break;
                case "logout":
                    response.getWriter().write(HttpPostService.logout(request));
                    break;
            }
        } catch (Exception e) {
            response.sendError(404, e.getMessage());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] urlsPath = request.getPathInfo().split("/");
        String urlPath = urlsPath[1];
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            switch (urlPath) {
                case "plays":
                    if (urlsPath.length == 3) {
                        int playId = Integer.parseInt(urlsPath[2]);
                        response.getWriter().write(HttpGetService.getPlay(playId));
                    }
                    response.getWriter().write(HttpGetService.getAllPlays());
                    break;
                case "auth":
                    response.getWriter().write(HttpGetService.getAuthUser(request));
                    break;
                default:
                    response.sendError(404);
            }

        } catch (Exception e) {
            response.sendError(404, e.getMessage());
        }
    }
}
