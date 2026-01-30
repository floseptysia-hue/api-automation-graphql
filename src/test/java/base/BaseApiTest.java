package base;

import helper.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import java.util.Map;

public class BaseApiTest {

    protected Map<String, String> cookies;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://lmsb2b.do.dibimbing.id";
        Response loginResponse = AuthHelper.login();
        cookies = loginResponse.getCookies();

        if (cookies.isEmpty()) {
            throw new RuntimeException("COOKIE KOSONG — LOGIN GAGAL");
        }
    }
}
