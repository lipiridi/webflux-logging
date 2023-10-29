package com.gtihub.lipiridi.webflux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;


public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Nullable
    public static JsonNode getContainerJsonNode(String jsonString) {
        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonString);
            return jsonNode.isContainerNode() ? jsonNode : null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
