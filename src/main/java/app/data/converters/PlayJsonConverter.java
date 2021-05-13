package app.data.converters;

import app.entities.Play;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PlayJsonConverter implements JsonSerializer<Play> {
    @Override
    public JsonElement serialize(Play play, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", play.getId());
        jsonObject.addProperty("name", play.getName());
        jsonObject.add("author", jsonSerializationContext.serialize(play.getAuthor()));
        jsonObject.add("genre", jsonSerializationContext.serialize(play.getGenre()));
        jsonObject.add("dates", jsonSerializationContext.serialize(play.getDates()));
        return jsonObject;
    }
}