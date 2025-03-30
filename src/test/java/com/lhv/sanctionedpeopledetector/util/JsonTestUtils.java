package com.lhv.sanctionedpeopledetector.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public final class JsonTestUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setDateFormat(new StdDateFormat());
        OBJECT_MAPPER.setTimeZone(TimeZone.getDefault());
        OBJECT_MAPPER.registerModules(new Jdk8Module(), new JavaTimeModule());
        OBJECT_MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static String asJsonString(final Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "Error received while trying to convert Object to JSON String. Error: " + e;
        }
    }

    public static <T> T getObjectFromJsonString(String jsonString, Class<T> type) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, type);
    }

    public static <T> T getObjectFromJsonString(String jsonString, TypeReference<T> type) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, type);
    }

}
