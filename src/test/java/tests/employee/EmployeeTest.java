package tests.employee;

import api.EmployeeApi;
import base.BaseApiTest;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.TestDataBuilderEmployee;

import static org.testng.Assert.*;

public class EmployeeTest extends BaseApiTest {

    @Test(description = "EMP_TC002 - Verify user can successfully add a new employee")
    public void addEmployee_success() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.validEmployee()
        );

        String employeeId = response.jsonPath()
                .getString("data.createEmployee.id");

        assertNotNull(employeeId, "Employee ID should not be null");
    }

    @Test(description = "EMP_TC003 - Verify system rejects submission if mandatory fields are empty",
        enabled = false
    )
    public void addEmployeeMandatoryField_failed() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.employeeWithEmptyMandatoryField()
        );

        String employeeId = response.jsonPath()
                .getString("data.createEmployee.id");

        assertNull(
                employeeId,
                "BUG: Employee still created even when mandatory field is empty"
        );
    }

    @Test(description = "EMP_TC004 - Verify system rejects invalid email format")
    public void addEmployeeInvalidFormatEmail_failed() {
        Response response = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.employeeWithInvalidEmail()
        );

        String employeeId = response.jsonPath()
                .getString("data.createEmployee.id");

        assertNull(
                employeeId,
                "BUG: Employee still created even invalid email format"
        );
    }

    @Test(description = "EMP_TC005 - Verify system rejects duplicate Employee ID",
        enabled = false
    )

    public void addEmployeeDuplicateEmployeeId_failed() {
        // STEP 1: create first employee (valid)
        JSONObject firstEmployee = TestDataBuilderEmployee.validEmployee();
        Response firstResponse = EmployeeApi.addEmployee(cookies, firstEmployee);

        String employeeId = firstEmployee.getString("employeeId");
        String createdId = firstResponse.jsonPath().getString("data.createEmployee.id");

        assertNotNull(createdId, "Precondition failed: first employee not created");

        // STEP 2: create second employee with SAME employeeId
        JSONObject duplicateEmployee =
                TestDataBuilderEmployee.employeeWithDuplicateEmployeeId(employeeId);

        Response duplicateResponse =
                EmployeeApi.addEmployee(cookies, duplicateEmployee);

        String duplicateCreatedId =
                duplicateResponse.jsonPath().getString("data.createEmployee.id");

        // STEP 3: assertion (NEGATIVE CASE)
        assertNull(
                duplicateCreatedId,
                "BUG: Employee still created with duplicate Employee ID"
        );
    }

    @Test(description = "EMP_TC017 - Verify user can inactivate employee account")
    public void inactivateEmployee_success() {
        // === PRECONDITION: CREATE EMPLOYEE ===
        Response createResponse = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.validEmployee()
        );
        String employeeId = createResponse.jsonPath().getString("data.createEmployee.id");
        assertNotNull(employeeId, "Precondition failed: employee not created");

        Response inactivateResponse = EmployeeApi.inactivateEmployee(
                cookies,
                employeeId
        );
        Object inactivateResult = inactivateResponse.jsonPath().get("data.inactivateEmployee");
        assertNotNull(
                inactivateResult,
                "BUG: inactivateEmployee returned null (possible GraphQL error)"
        );
        assertTrue(
                Boolean.parseBoolean(inactivateResult.toString()),
                "Employee should be successfully inactivated"
        );
    }

    @Test(description = "EMP_TC017 - Verify user can delete employee account")
    public void deleteEmployee_success() {
        // === PRECONDITION: CREATE EMPLOYEE ===
        Response createResponse = EmployeeApi.addEmployee(
                cookies,
                TestDataBuilderEmployee.validEmployee()
        );
        String employeeId = createResponse.jsonPath().getString("data.createEmployee.id");

        Response deleteResponse = EmployeeApi.deleteEmployee(
                cookies,
                employeeId
        );
        Object deleteResult = deleteResponse.jsonPath().get("data.deleteEmployee");
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
