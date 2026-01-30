package utils;

import org.json.JSONObject;

public class TestDataBuilderEmployee {

    private static JSONObject baseValidEmployee() {
        JSONObject input = new JSONObject();
        input.put("name", "Test Florida");
        input.put("employeeId", "EMP-" + System.currentTimeMillis());
        input.put("email", "florida_x" + System.currentTimeMillis() + "@gmail.com");
        input.put("phoneNumber", "8223812391893");
        input.put("divisionId", "96933daa-2774-41f8-a4ca-120a47a9b760");
        input.put("employeeRole", "Karyawan");
        input.put("angkatanId", 1);
        input.put("gender", "female");
        input.put("dateOfBirth", "1999-03-16T00:00:00.000Z");
        input.put("address", "Jakarta Selatan");
        input.put("nik", "1231231231231");
        input.put("npwp", "2313123123123");
        return input;
    }

    public static JSONObject validEmployee() {
        return new JSONObject(baseValidEmployee().toString());
    }

    public static JSONObject employeeWithEmptyMandatoryField() {
        JSONObject data = baseValidEmployee();
        data.put("name", "");
        return data;
    }

    public static JSONObject employeeWithInvalidEmail() {
        JSONObject data = baseValidEmployee();
        data.put("email", "florida_xgmail.com");
        return data;
    }

    public static JSONObject employeeWithDuplicateEmployeeId(String employeeId) {
        JSONObject data = baseValidEmployee();
        data.put("email", "florida_x" + System.currentTimeMillis() + "@gmail.com");
        data.put("employeeId", employeeId);
        return data;
    }

    public static JSONObject employeeWithSpecificEmployeeId(String employeeId) {
        JSONObject data = baseValidEmployee();
        data.put("employeeId", employeeId);
        return data;
    }
}
