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
    private boolean _prettyPrint;
    private Map<String, Object> _configInUse;

    public JacksonGeneratorFactory(JsonFactory factory) {
        _factory = factory;
    }

    public JacksonGeneratorFactory(Map<String, ?> config) {
        _factory = new JsonFactory();
        _configInUse = ConfigurationUtils.configure(_factory, config);
        if(config != null && config.containsKey(JsonGenerator.PRETTY_PRINTING)){
            _prettyPrint = true;
            _configInUse.put(JsonGenerator.PRETTY_PRINTING, (Boolean)_prettyPrint);
        }
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        try {
            com.fasterxml.jackson.core.JsonGenerator generator = _factory.createGenerator(writer);
            if(_prettyPrint){
                generator.useDefaultPrettyPrinter();
            }
            return new JacksonGenerator(generator);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        try {
        com.fasterxml.jackson.core.JsonGenerator generator = _factory.createGenerator(out);
        if(_prettyPrint){
            generator.useDefaultPrettyPrinter();
        }
            return new JacksonGenerator(generator);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out, Charset charset) {
        try {
        com.fasterxml.jackson.core.JsonGenerator generator = _factory.createGenerator(new OutputStreamWriter(out, charset));
        if(_prettyPrint){
            generator.useDefaultPrettyPrinter();
        }
            return new JacksonGenerator(generator);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return _configInUse;
    }
}
