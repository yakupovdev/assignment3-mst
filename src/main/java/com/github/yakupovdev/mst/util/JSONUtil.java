package com.github.yakupovdev.mst.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.yakupovdev.mst.model.GraphResult;
import com.github.yakupovdev.mst.model.JSONInput;

import java.io.File;
import java.util.List;
import java.util.Map;

public class JSONUtil {
    private static final ObjectMapper M = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static JSONInput readAssignmentInput(File f) throws java.io.IOException {
        return M.readValue(f, JSONInput.class);
    }

    public static void writeResults(File out, List<GraphResult> results) throws java.io.IOException {
        Map<String, Object> wrapper = Map.of("results", results);
        M.writeValue(out, wrapper);
    }
}

