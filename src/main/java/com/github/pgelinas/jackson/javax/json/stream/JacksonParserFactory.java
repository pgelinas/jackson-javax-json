package com.github.pgelinas.jackson.javax.json.stream;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.json.*;
import javax.json.stream.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.pgelinas.jackson.javax.json.*;

public class JacksonParserFactory implements JsonParserFactory {
    private final JsonFactory _factory;
    private Map<String, Object> _configInUse;
    private final NodeFactory _nodeFactory;

    public JacksonParserFactory(JsonFactory factory, NodeFactory nodeFactory) {
        _factory = factory;
        _nodeFactory = nodeFactory;
    }

    public JacksonParserFactory(Map<String, ?> config, NodeFactory nodeFactory) {
        this(new JsonFactory(), nodeFactory);
        _configInUse = ConfigurationUtils.configure(_factory, config);
    }

    @Override
    public javax.json.stream.JsonParser createParser(Reader reader) {
        try {
            return new JacksonParser(_factory.createParser(reader));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public javax.json.stream.JsonParser createParser(InputStream in) {
        try {
            return new JacksonParser(_factory.createParser(in));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public javax.json.stream.JsonParser createParser(InputStream in, Charset charset) {
        try {
            return new JacksonParser(_factory.createParser(new InputStreamReader(in, charset)));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public javax.json.stream.JsonParser createParser(JsonObject obj) {
        return parseTree(obj);
    }

    @Override
    public javax.json.stream.JsonParser createParser(JsonArray array) {
        return parseTree(array);
    }

    private javax.json.stream.JsonParser parseTree(JsonValue value) {
        JsonNode node;
        if (value instanceof JacksonValue) {
            node = ((JacksonValue<?>) value).delegate();
        } else {
            node = _nodeFactory.from(value);
        }

        return new JacksonParser(node.traverse());
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return _configInUse;
    }
}
