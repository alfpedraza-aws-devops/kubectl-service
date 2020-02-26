package mx.pedraza.kubernetes_api.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jayway.jsonpath.JsonPath;

import org.springframework.stereotype.Service;

import net.minidev.json.JSONArray;

/**
 * 
 */
@Service
public class JsonHelper {

    /**
     * 
     * @param json
     * @return
     */
    public boolean isValid(String json) {
        try {
            JsonParser parser = new JsonParser();
            parser.parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    /**
     * 
     * @param json
     * @return
     */
    public int getElementLength(String json) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        if (element instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject)element;
            if (jsonObject.has("kind") && jsonObject.get("kind").getAsString().equals("List")) {
                JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();
                int result = jsonArray.size();
                return result;
            } else {
                return 1;
            }
        } else if (element instanceof JsonArray) {
            JsonArray jsonArray = (JsonArray)element;
            int result = jsonArray.size();
            return result;
        } else {
            return 0;
        }
    }

    /**
     * 
     * @param json
     * @param path
     * @return
     */
    public String read(String json, String path) {
        // A query always returns a JSONArray.
        JSONArray array = JsonPath.read(json, path);
        if (array.size() == 0) return "";
        String result = array.get(0).toString();
        return result;
    }
}