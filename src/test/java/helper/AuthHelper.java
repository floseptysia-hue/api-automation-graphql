package helper;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class AuthHelper {

    public static Response login() {

        String query = """
            mutation login($usernameOrEmail: String!, $password: String!, $companyId: String!) {
              login(
                usernameOrEmail: $usernameOrEmail
                password: $password
                companyId: $companyId
              ) {
                user {
                  id
                  username
                }
                errors {
                  field
                  message
                }
              }
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("usernameOrEmail", "arwendymelyn@dibimbing.id");
        variables.put("password", "s2et9bh6l");
        variables.put("companyId", "811637b1-9989-4d45-a9f5-220c5f2354f7");

        JSONObject body = new JSONObject();
        body.put("query", query);
        body.put("operationName", "login");
        body.put("variables", variables);

        Response response =
                given()
                        .contentType("application/json")
                        .body(body.toString())
                        .when()
                        .post("/graphql")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Kalau login error → stop
        if (response.jsonPath().getList("data.login.errors") != null &&
                !response.jsonPath().getList("data.login.errors").isEmpty()) {

            throw new RuntimeException(
                    "Login failed: " +
                            response.jsonPath().getString("data.login.errors[0].message")
            );
        }

        return response;
    }
}
