package tests.employee;

import api.EmployeeApi;
import base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.TestDataBuilderEmployee;

import static org.testng.Assert.*;

@Epic("API Automation")
@Feature("Employee GraphQL")
public class EmployeeTest extends BaseApiTest {

    @Test(description = "EMP_TC002 - Verify user can successfully add a new employee")
    @Story("Create Employee")
    @Severity(SeverityLevel.CRITICAL)
    public void addEmployee_success() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.validEmployee()
        );

        Allure.addAttachment(
                "Create Employee Response",
                "application/json",
                response.asPrettyString()
        );

        String employeeId =
                response.jsonPath().getString("data.createEmployee.id");

        assertNotNull(employeeId, "Employee ID should not be null");
    }

    @Test(
            description = "EMP_TC003 - Verify system rejects submission if mandatory fields are empty",
            enabled = false
    )
    @Story("Create Employee")
    @Severity(SeverityLevel.NORMAL)
    public void addEmployeeMandatoryField_failed() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.employeeWithEmptyMandatoryField()
        );

        Allure.addAttachment(
                "Empty Mandatory Field Response",
                "application/json",
                response.asPrettyString()
        );

        String employeeId =
                response.jsonPath().getString("data.createEmployee.id");

        assertNull(
                employeeId,
                "BUG: Employee still created even when mandatory field is empty"
        );
    }

    @Test(description = "EMP_TC004 - Verify system rejects invalid email format")
    @Story("Create Employee Validation")
    @Severity(SeverityLevel.NORMAL)
    public void addEmployeeInvalidFormatEmail_failed() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.employeeWithInvalidEmail()
        );

        Allure.addAttachment(
                "Invalid Email Response",
                "application/json",
                response.asPrettyString()
        );

        String employeeId =
                response.jsonPath().getString("data.createEmployee.id");

        assertNull(
                employeeId,
                "BUG: Employee still created even invalid email format"
        );
    }

    @Test(
            description = "EMP_TC005 - Verify system rejects duplicate Employee ID",
            enabled = false
    )
    @Story("Create Employee Validation")
    @Severity(SeverityLevel.NORMAL)
    public void addEmployeeDuplicateEmployeeId_failed() {
        // === STEP 1: CREATE EMPLOYEE ===
        JSONObject firstEmployee =
                TestDataBuilderEmployee.validEmployee();

        Response firstResponse =
                EmployeeApi.addEmployee(cookies, firstEmployee);

        Allure.addAttachment(
                "Create Employee Response",
                "application/json",
                firstResponse.asPrettyString()
        );

        String employeeId =
                firstEmployee.getString("employeeId");

        String createdId =
                firstResponse.jsonPath().getString("data.createEmployee.id");

        assertNotNull(
                createdId,
                "Precondition failed: first employee not created"
        );

        // === STEP 2: CREATE DUPLICATE EMPLOYEE ===
        JSONObject duplicateEmployee =
                TestDataBuilderEmployee.employeeWithDuplicateEmployeeId(employeeId);

        Response duplicateResponse =
                EmployeeApi.addEmployee(cookies, duplicateEmployee);

        Allure.addAttachment(
                "Duplicate Employee Response",
                "application/json",
                duplicateResponse.asPrettyString()
        );

        String duplicateCreatedId =
                duplicateResponse.jsonPath().getString("data.createEmployee.id");

        // === STEP 3: ASSERTION ===
        assertNull(
                duplicateCreatedId,
                "BUG: Employee still created with duplicate Employee ID"
        );
    }

    @Test(description = "EMP_TC017 - Verify user can inactivate employee account")
    @Story("Employee Lifecycle")
    @Severity(SeverityLevel.CRITICAL)
    public void inactivateEmployee_success() {
        // === PRECONDITION: CREATE EMPLOYEE ===
        Response createResponse =
                EmployeeApi.addEmployee(
                        cookies,
                        TestDataBuilderEmployee.validEmployee()
                );

        Allure.addAttachment(
                "Create Employee Response",
                "application/json",
                createResponse.asPrettyString()
        );

        String employeeId =
                createResponse.jsonPath().getString("data.createEmployee.id");

        assertNotNull(
                employeeId,
                "Precondition failed: employee not created"
        );

        // === INACTIVATE EMPLOYEE ===
        Response inactivateResponse =
                EmployeeApi.inactivateEmployee(cookies, employeeId);

        Allure.addAttachment(
                "Inactivate Employee Response",
                "application/json",
                inactivateResponse.asPrettyString()
        );

        Object inactivateResult =
                inactivateResponse.jsonPath().get("data.inactivateEmployee");

        assertNotNull(
                inactivateResult,
                "BUG: inactivateEmployee returned null (possible GraphQL error)"
        );

        assertTrue(
                Boolean.parseBoolean(inactivateResult.toString()),
                "Employee should be successfully inactivated"
        );
    }

    @Test(description = "EMP_TC018 - Verify user can delete employee account")
    @Story("Employee Lifecycle")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteEmployee_success() {
        // === PRECONDITION: CREATE EMPLOYEE ===
        Response createResponse =
                EmployeeApi.addEmployee(
                        cookies,
                        TestDataBuilderEmployee.validEmployee()
                );

        Allure.addAttachment(
                "Create Employee Response",
                "application/json",
                createResponse.asPrettyString()
        );

        String employeeId =
                createResponse.jsonPath().getString("data.createEmployee.id");

        assertNotNull(
                employeeId,
                "Precondition failed: employee not created"
        );

        // === DELETE EMPLOYEE ===
        Response deleteResponse =
                EmployeeApi.deleteEmployee(cookies, employeeId);

        Allure.addAttachment(
                "Delete Employee Response",
                "application/json",
                deleteResponse.asPrettyString()
        );

        Object deleteResult =
                deleteResponse.jsonPath().get("data.deleteEmployee");

        assertNotNull(
                deleteResult,
                "BUG: deleteEmployee returned null (possible GraphQL error)"
        );

        assertTrue(
                Boolean.parseBoolean(deleteResult.toString()),
                "Employee should be successfully deleted"
        );
    }
}
