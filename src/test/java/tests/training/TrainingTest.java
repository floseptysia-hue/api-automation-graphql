package tests.training;

import api.EmployeeApi;
import api.TrainingApi;
import base.BaseApiTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataBuilderEmployee;
import utils.TestDataBuilderTraining;

import static org.testng.Assert.assertNotNull;

public class TrainingTest extends BaseApiTest {

    @Test(description = "TRN_TC002 - Verify user can successfully add new training")
    public void addProgram_success() {
        Response response = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.validTraining()
        );

        String programId = response.jsonPath()
                .getString("data.createProgram.id");

        assertNotNull(programId, "Program ID should not be null");
    }

    @Test(description = "TRN_TC003 - Verify user can successfully add new list of chapter")
    public void addChapter_success() {
        Response response = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.validTraining()
        );

        String programId = response.jsonPath().getString("data.createProgram.id");

        Response addNewChapter = TrainingApi.addChapter(
                cookies,
                TestDataBuilderTraining.validChapter(programId)
        );

        Object inactivateResult = addNewChapter.jsonPath().get("data.createChapter");
        assertNotNull(inactivateResult, "Chapter ID should not be null");
    }
}
