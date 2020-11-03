package domain;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JsonGenerator {
    private String login;
    private String password;
    private String status;

    public String user(String name, String data, boolean active) {
        return active ? new Gson().toJson(new JsonGenerator(name, data, "active")) : new Gson().toJson(new JsonGenerator(name, data, "blocked"));
    }
}