package com.github.pgelinas.jackson.javax.json.stream;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.json.*;
import javax.json.stream.*;
import javax.json.stream.JsonGenerator;

import com.fasterxml.jackson.core.*;
import com.github.pgelinas.jackson.javax.json.*;

public class JacksonGeneratorFactory implements JsonGeneratorFactory {
    private final JsonFactory _factory;

    public JacksonGeneratorFactory(JsonFactory factory) {
        _factory = factory;
    }

    public JacksonGeneratorFactory(Map<String, ?> config) {
        _factory = new JsonFactory();
        ConfigurationUtils.configure(_factory, config);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        try {
            return new JacksonGenerator(_factory.createGenerator(writer));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        try {
            return new JacksonGenerator(_factory.createGenerator(out));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out, Charset charset) {
        try {
            return new JacksonGenerator(_factory.createGenerator(new OutputStreamWriter(out, charset)));
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return ConfigurationUtils.factoryConfiguration();
    }
}
