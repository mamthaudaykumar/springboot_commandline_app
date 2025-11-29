package com.motilent.entrolytics_notifier.fileutility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

@Component
public class JsonFileReader implements FileReader {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonFileReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String readFile(String path) throws IOException {
        JsonNode node = objectMapper.readTree(new File(path));

        return objectMapper.writeValueAsString(node);
    }
}
