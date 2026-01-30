package utils;

import org.json.JSONObject;

public class TestDataBuilderTraining {
    private static JSONObject baseValidTrainig() {
        JSONObject input = new JSONObject();
        input.put("title", "TTILE-FLO-" + System.currentTimeMillis());
        input.put("description", "test description");
        input.put("type", "training");
        input.put("isSequential", false);
        return input;
    }

    public static JSONObject validTraining() {
        return new JSONObject(baseValidTrainig().toString());
    }

    private static JSONObject baseValidChapter(String programId) {
        JSONObject input = new JSONObject();
        input.put("title", "CHAPTER-FLO-" + System.currentTimeMillis());
        input.put("description", "test description");
        input.put("order", 0);
        input.put("programId", programId);
        return input;
    }

    public static JSONObject validChapter(String programId) {
        return new JSONObject(baseValidChapter(programId).toString());
    }
}