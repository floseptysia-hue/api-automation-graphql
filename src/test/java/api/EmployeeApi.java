package api;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class EmployeeApi {

    public static Response addEmployee(Map<String, String> cookies, JSONObject employeeInput) {
        String query = """
            mutation createEmployee($input: EmployeeInput!) {
              createEmployee(input: $input) {
                id
              }
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("input", employeeInput);

        JSONObject body = new JSONObject();
        body.put("query", query);
        body.put("operationName", "createEmployee");
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

    public static Response editEmployee(String token, JSONObject variables) {
        String query = """
            mutation EditEmployee($id: ID!, $name: String!) {
              editEmployee(id: $id, name: $name) {
                message
              }
            }
        """;

        JSONObject body = GraphQLRequest.build(query, variables);

        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/graphql")
                .then()
                .extract()
                .response();
    }

    public static Response deleteEmployee(Map<String, String> cookies, String employeeId) {
        String query = """
            mutation deleteEmployee($id: String!) {
              deleteEmployee(id: $id)
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("id", employeeId);

        JSONObject body = GraphQLRequest.build(query, variables);

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

    public static Response inactivateEmployee(Map<String, String> cookies, String employeeId) {
        String query = """
            mutation inactivateEmployee($id: String!) {
              inactivateEmployee(id: $id)
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("id", employeeId);

        JSONObject body = GraphQLRequest.build(query, variables);

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

    public static Response searchDivision(String token, String keyword) {

        String query = """
            query SearchDivision($keyword: String!) {
              searchDivision(keyword: $keyword) {
                id
                name
              }
            }
        """;

        JSONObject variables = new JSONObject();
        variables.put("keyword", keyword);

        JSONObject body = GraphQLRequest.build(query, variables);

        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/graphql")
                .then()
                .extract()
                .response();
    }
}
