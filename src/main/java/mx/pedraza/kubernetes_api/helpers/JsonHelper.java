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
 * Performs read operations with JSON strings.
 */
@Service
public class JsonHelper {

    /**
     * Determines whether the specified JSON string is valid or not.
     * @param json The JSON string to validate.
     * @return True if the input is valid, False otherwise.
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
     * Gets the number of root level child elements in the JSON string.
     * Since Kubernetes returns an array of objects using a "List" element,
     * it performs a couple of validations to overcome this feature.
     * @param json The JSON string to inspect.
     * @return An integer specifing the count of root level child elements.
     */
    public int getArrayLength(String json) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);

        if (element instanceof JsonObject) {
            return getElementLength(element);
        } else if (element instanceof JsonArray) {
            return ((JsonArray)element).size();
        } else {
            return 0;
        }
    }

    // Inspects the JSONElement to see if it's of kind "List".
    // If that's the case, get its "items" array and return its size.
    private int getElementLength(JsonElement element) {
        JsonObject jsonObject = (JsonObject)element;
        if (jsonObject.has("kind") && jsonObject.get("kind").getAsString().equals("List")) {
            JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();
            return jsonArray.size();
        } else {
            return 1;
        }
    }

    /**
     * Returns the value specified in the JSONPath path.
     * @param json The JSON string to inspect.
     * @param path The JSONPath query to use.
     * @return A string value containing the result of the query.
     */
    public String read(String json, String path) {
        // A query always returns a JSONArray.
        JSONArray array = JsonPath.read(json, path);
        if (array.size() == 0) return "";
        String result = array.get(0).toString();
        return result;
    }
}