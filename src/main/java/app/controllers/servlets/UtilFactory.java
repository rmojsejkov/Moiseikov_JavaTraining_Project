package app.controllers.servlets;

import app.data.converters.LoginJsonConverter;
import app.data.converters.PlayJsonConverter;
import app.entities.Login;
import app.entities.Play;
import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public final class UtilFactory {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Play.class, new PlayJsonConverter())
            .registerTypeAdapter(Login.class, new LoginJsonConverter())
            .create();  

    private UtilFactory() {

    }

    public static Gson getGSON() {
        return GSON;
    }

    public static String convertHttpRequestToString(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new JsonSyntaxException(request.toString());
        }
    }

    public static JsonArray convertHttpRequestToJsonArray(HttpServletRequest request) {
        return GSON.fromJson(convertHttpRequestToString(request), JsonArray.class);
    }

    public static JsonObject convertHttpRequestToJsonObject(HttpServletRequest request) {
        return GSON.fromJson(convertHttpRequestToString(request), JsonObject.class);
    }
}
