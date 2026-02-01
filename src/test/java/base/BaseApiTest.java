package base;

import helper.AuthHelper;
import helper.EnvConfig;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.Map;

@Listeners({AllureTestNg.class})
public class BaseApiTest {

    protected Map<String, String> cookies;

    @BeforeClass
    public void setup() {
        String baseUrl = EnvConfig.get("BASE_URL");
        RestAssured.baseURI = baseUrl;
        Response loginResponse = AuthHelper.login();
        cookies = loginResponse.getCookies();

        if (cookies.isEmpty()) {
            throw new RuntimeException("COOKIE KOSONG — LOGIN GAGAL");
        }
    }
}
