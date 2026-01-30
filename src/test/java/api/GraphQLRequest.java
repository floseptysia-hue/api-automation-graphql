package api;

import org.json.JSONObject;

public class GraphQLRequest {

    public static JSONObject build(String query, JSONObject variables) {
        JSONObject body = new JSONObject();
        body.put("query", query);
        body.put("variables", variables);
        return body;
    }
}