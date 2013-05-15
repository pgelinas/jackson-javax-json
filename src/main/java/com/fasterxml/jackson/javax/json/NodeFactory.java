package com.fasterxml.jackson.javax.json;

import static java.lang.String.*;

import javax.json.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

public final class NodeFactory {
    public JsonValue from(JsonNode node) {
        JsonToken token = node.asToken();
        switch (token) {
            case START_OBJECT:
                return new JacksonObject((ObjectNode) node, this);
            case START_ARRAY:
                return new JacksonArray((ArrayNode) node, this);
            case VALUE_FALSE:
                return JsonValue.FALSE;
            case VALUE_NULL:
                return JsonValue.NULL;
            case VALUE_TRUE:
                return JsonValue.TRUE;
            case VALUE_STRING:
                return new JacksonString((TextNode) node);
            case VALUE_NUMBER_FLOAT:
            case VALUE_NUMBER_INT:
                return new JacksonNumber((NumericNode) node);
            default:
                throw new UnsupportedOperationException(format("Token '%s' isn't supported by the spec.", token));
        }
    }
}
