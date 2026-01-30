package api;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TrainingApi {

    public static Response addTraining(Map<String, String> cookies, JSONObject programInput) {
        String query = """
            mutation createProgram($input: ProgramInput!) {
              createProgram(input: $input) {
                id
              }
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("input", programInput);

        JSONObject body = new JSONObject();
        body.put("query", query);
        body.put("operationName", "createProgram");
        body.put("variables", variables);

        return given()
                .cookies(cookies)
                .contentType("application/json")
                .body(body.toString())
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response addChapter(Map<String, String> cookies, JSONObject chapterInput) {
        String query = """
            mutation createChapter($input: ChapterInput!) {
              createChapter(input: $input) {
                id
              }
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("input", chapterInput);

        JSONObject body = new JSONObject();
        body.put("query", query);
        body.put("operationName", "createChapter");
        body.put("variables", variables);

        return given()
                .cookies(cookies)
                .contentType("application/json")
                .body(body.toString())
                .when()
                .post("/graphql")
                .then()
                .log().all()
                .extract()
                .response();
    }
}
