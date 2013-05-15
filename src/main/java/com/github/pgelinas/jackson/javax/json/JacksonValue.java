package com.github.pgelinas.jackson.javax.json;

import javax.json.*;

import com.fasterxml.jackson.databind.*;

public interface JacksonValue<T extends JsonNode> extends JsonValue {
    T delegate();
}
