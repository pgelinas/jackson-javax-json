package com.fasterxml.jackson.javax.json.stream;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.json.*;
import javax.json.stream.*;
import javax.json.stream.JsonParser;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.javax.json.*;

public class JacksonParserFactory implements JsonParserFactory {
    private final JsonFactory _factory;

    public JacksonParserFactory(JsonFactory factory) {
        _factory = factory;
    }

    public JacksonParserFactory(Map<String, ?> config) {
        _factory = new JsonFactory();
    }

    @Override
    public JsonParser createParser(Reader reader) {
        try {
            return new JacksonParser(_factory.createParser(reader));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonParser createParser(InputStream in) {
        try {
            return new JacksonParser(_factory.createParser(in));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonParser createParser(InputStream in, Charset charset) {
        try {
            return new JacksonParser(_factory.createParser(new InputStreamReader(in, charset)));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    // TODO: what to do in case we received something else then this implementation's node? 
    // Probably re-use the JsonStructureParser from the RI?
    @Override
    public JsonParser createParser(JsonObject obj) {
        if(obj instanceof JacksonValue) {
            JacksonValue<?> node = (JacksonValue<?>) obj;
            return new JacksonParser(node.delegate().traverse());
        }
        throw new UnsupportedOperationException("Parser from object not implemented!");
    }

    @Override
    public JsonParser createParser(JsonArray array) {
        if(array instanceof JacksonValue) {
            JacksonValue<?> node = (JacksonValue<?>) array;
            return new JacksonParser(node.delegate().traverse());
        }
        throw new UnsupportedOperationException("Parser from array not implemented!");
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return Collections.emptyMap();
    }
}
