package app.data.converters;

import app.entities.Login;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LoginJsonConverter implements JsonDeserializer<Login>, JsonSerializer<Login> {

    @Override
    public Login deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int id = 0;
        if (jsonObject.get("id") != null) {
            id = jsonObject.get("id").getAsInt();
        }
        String login = jsonObject.get("login").getAsString();
        String password = jsonObject.get("password").getAsString();
        String email = jsonObject.get("email").getAsString();
        String fullName = jsonObject.get("fullName").getAsString();
        int phone = jsonObject.get("phone").getAsInt();

        return new Login(id, login, password,fullName, email, phone, 0);
    }

    @Override
    public JsonElement serialize(Login login, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", login.getId());
        jsonObject.addProperty("login", login.getLogin());
        jsonObject.addProperty("email", login.getEmail());
        jsonObject.addProperty("fullName", login.getFullName());
        jsonObject.addProperty("phone", login.getPhone());
        return jsonObject;
    }
}
