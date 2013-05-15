package com.fasterxml.jackson.javax.json.spi;

import java.io.*;
import java.util.*;

import javax.json.*;
import javax.json.spi.*;
import javax.json.stream.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.javax.json.*;
import com.fasterxml.jackson.javax.json.stream.*;

public class JacksonProvider extends JsonProvider {
    private final JacksonParserFactory _parserFactory;
    private final JacksonGeneratorFactory _generatorFactory;
    private final ObjectMapper _mapper = new ObjectMapper();
    private final NodeFactory _nodeFactory = new NodeFactory();
    
    public JacksonProvider() {
        _parserFactory = new JacksonParserFactory(_mapper.getFactory());
        _generatorFactory = new JacksonGeneratorFactory(_mapper.getFactory());
    }

    @Override
    public JsonParser createParser(Reader reader) {
        return _parserFactory.createParser(reader);
    }

    @Override
    public JsonParser createParser(InputStream in) {
        return _parserFactory.createParser(in);
    }

    @Override
    public JsonParserFactory createParserFactory(Map<String, ?> config) {
        return new JacksonParserFactory(config);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        return _generatorFactory.createGenerator(writer);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        return _generatorFactory.createGenerator(out);
    }

    @Override
    public JsonGeneratorFactory createGeneratorFactory(Map<String, ?> config) {
        return new JacksonGeneratorFactory(config);
    }

    @Override
    public JsonReader createReader(Reader reader) {
        return new JacksonReader(_mapper, _nodeFactory, reader);
    }

    @Override
    public JsonReader createReader(InputStream in) {
        return new JacksonReader(_mapper, _nodeFactory, in);
    }

    @Override
    public JsonWriter createWriter(Writer writer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonWriter createWriter(OutputStream out) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonWriterFactory createWriterFactory(Map<String, ?> config) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonReaderFactory createReaderFactory(Map<String, ?> config) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonObjectBuilder createObjectBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonArrayBuilder createArrayBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonBuilderFactory createBuilderFactory(Map<String, ?> config) {
        // TODO Auto-generated method stub
        return null;
    }

}
