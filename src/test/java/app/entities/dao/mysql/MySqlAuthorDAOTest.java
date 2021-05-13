package app.entities.dao.mysql;

import app.entities.dao.factory.DAOFactory;
import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySqlAuthorDAOTest {

    @Test
    public void getAllAuthors() {
        DAOFactory.getDb("mysql").getAuthorDAO().getAllAuthors();
    }

    @Test
    public void JsonCheck() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("test", "super test");
        System.out.println(jsonObject);
    }
}