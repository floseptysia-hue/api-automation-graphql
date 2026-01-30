package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class ResponseValidator {

    public static void assertSuccess(Response response, String messagePath, String expectedMessage) {
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertNull(response.jsonPath().get("errors"));
        Assert.assertEquals(response.jsonPath().getString(messagePath), expectedMessage);
    }

    public static void assertHasError(Response response) {
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertNotNull(response.jsonPath().get("errors"));
    }
}
