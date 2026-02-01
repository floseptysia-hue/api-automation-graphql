package tests.training;

import api.EmployeeApi;
import api.TrainingApi;
import base.BaseApiTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.TestDataBuilderEmployee;
import utils.TestDataBuilderTraining;

import static org.testng.Assert.*;

public class TrainingTest extends BaseApiTest {

    @Test(description = "TRN_TC002 - Verify user can successfully add new training")
    @Story("Create Training Program")
    @Severity(SeverityLevel.CRITICAL)
    public void addProgram_success() {
        Response response = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.validTraining()
        );

        Allure.addAttachment(
                "Create Training Response",
                "application/json",
                response.asPrettyString()
        );

        String programId = response.jsonPath()
                .getString("data.createProgram.id");

        assertNotNull(programId, "Program ID should not be null");
    }

    @Test(description = "TRN_TC003 - Verify user can successfully add new chapter")
    @Story("Create Training Chapter")
    @Severity(SeverityLevel.NORMAL)
    public void addChapter_success() {
        // Step 1: Create program
        Response createProgramResponse = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.validTraining()
        );

        String programId = createProgramResponse.jsonPath()
                .getString("data.createProgram.id");

        assertNotNull(programId, "Program ID should not be null");

        Allure.addAttachment(
                "Create Program Response",
                "application/json",
                createProgramResponse.asPrettyString()
        );

        // Step 2: Add new chapter
        Response addChapterResponse = TrainingApi.addChapter(
                cookies,
                TestDataBuilderTraining.validChapter(programId)
        );

        Allure.addAttachment(
                "Add Chapter Response",
                "application/json",
                addChapterResponse.asPrettyString()
        );

        String chapterId = addChapterResponse.jsonPath()
                .getString("data.createChapter.id");

        assertNotNull(chapterId, "Chapter ID should not be null");
    }

    @Test(description = "TRN_TC004 - Verify system rejects submission if mandatory fields are empty")
    @Story("Create Training Program")
    @Severity(SeverityLevel.NORMAL)
    public void addProgramMandatoryField_failed() {
        Response response = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.programWithEmptyMandatoryField()
        );

        Allure.addAttachment(
                "Empty Mandatory Field Response",
                "application/json",
                response.asPrettyString()
        );

        String programId = response.jsonPath()
                .getString("data.createProgram.id");

        assertNull(
                programId,
                "BUG: Program is created even when mandatory field is empty"
        );
    }

    @Test(description = "TRN_TC005 - Verify user can edit Training information")
    @Story("Update Training")
    @Severity(SeverityLevel.NORMAL)
    public void updateProgram_success() {
        // Step 1: Create program
        Response createProgramResponse = TrainingApi.addTraining(
                cookies,
                TestDataBuilderTraining.validTraining()
        );

        Allure.addAttachment(
                "Create Program Response",
                "application/json",
                createProgramResponse.asPrettyString()
        );

        String programId = createProgramResponse.jsonPath()
                .getString("data.createProgram.id");

        assertNotNull(programId, "Program ID should not be null");

        // Step 2: Update program
        Response updateProgramResponse = TrainingApi.updateTraining(
                cookies,
                programId,
                TestDataBuilderTraining.updateTraining()
        );

        Allure.addAttachment(
                "Update Program Response",
                "application/json",
                updateProgramResponse.asPrettyString()
        );

        String updatedProgramId = updateProgramResponse.jsonPath()
                .getString("data.updateProgram.id");

        assertEquals(
                updatedProgramId,
                programId,
                "Program ID should remain the same after update"
        );
    }
}
