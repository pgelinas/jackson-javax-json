package com.github.pgelinas.jackson.javax.json.stream;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.json.*;
import javax.json.stream.*;

import com.fasterxml.jackson.core.*;
import com.github.pgelinas.jackson.javax.json.*;

public class JacksonParserFactory implements JsonParserFactory {
    private final JsonFactory _factory;
    private Map<String, Object> _configInUse;

    public JacksonParserFactory(JsonFactory factory) {
        _factory = factory;
    }

    public JacksonParserFactory(Map<String, ?> config) {
        _factory = new JsonFactory();
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

    // TODO: what to do in case we received something else then this implementation's node? 
    // Probably re-use the JsonStructureParser from the RI?
    @Override
    public javax.json.stream.JsonParser createParser(JsonObject obj) {
        if (obj instanceof JacksonValue) {
            JacksonValue<?> node = (JacksonValue<?>) obj;
            return new JacksonParser(node.delegate().traverse());
        }
        throw new UnsupportedOperationException("No compatibility with other implementation yet.");
    }

    @Override
    public javax.json.stream.JsonParser createParser(JsonArray array) {
        if (array instanceof JacksonValue) {
            JacksonValue<?> node = (JacksonValue<?>) array;
            return new JacksonParser(node.delegate().traverse());
        }
        throw new UnsupportedOperationException("No compatibility with other implementation yet.");
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return _configInUse;
    }
}
