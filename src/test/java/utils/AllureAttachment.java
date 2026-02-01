package utils;

import io.qameta.allure.Allure;

public class AllureAttachment {

    public static void attachJson(String title, String body) {
        Allure.addAttachment(title, "application/json", body, ".json");
    }
}
