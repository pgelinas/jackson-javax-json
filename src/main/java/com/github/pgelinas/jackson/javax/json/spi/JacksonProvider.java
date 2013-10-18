package com.github.pgelinas.jackson.javax.json.spi;

import java.io.*;
import java.util.*;

import javax.json.*;
import javax.json.spi.*;
import javax.json.stream.*;

import com.fasterxml.jackson.databind.*;
import com.github.pgelinas.jackson.javax.json.*;
import com.github.pgelinas.jackson.javax.json.stream.*;

public class JacksonProvider extends JsonProvider {
    private final ObjectMapper _mapper = new ObjectMapper();
    private final NodeFactory _nodeFactory = new NodeFactory(_mapper.getNodeFactory());
    // Factories
    private final JacksonParserFactory _parserFactory;
    private final JacksonGeneratorFactory _generatorFactory;
    private final JacksonWriterFactory _writerFactory;
    private final JacksonReaderFactory _readerFactory;
    private final JacksonBuilderFactory _builderFactory;
    
    
    public JacksonProvider() {
        _parserFactory = new JacksonParserFactory(_mapper.getFactory(), _nodeFactory);
        _generatorFactory = new JacksonGeneratorFactory(_mapper.getFactory());
        _writerFactory = new JacksonWriterFactory(_mapper);
        _readerFactory = new JacksonReaderFactory(_mapper, _nodeFactory);
        _builderFactory = new JacksonBuilderFactory(_mapper, _nodeFactory);
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
        return new JacksonParserFactory(config, _nodeFactory);
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
        return _readerFactory.createReader(reader);
    }

    @Override
    public JsonReader createReader(InputStream in) {
        return _readerFactory.createReader(in);
    }

    @Override
    public JsonWriter createWriter(Writer writer) {
        return _writerFactory.createWriter(writer);
    }

    @Override
    public JsonWriter createWriter(OutputStream out) {
        return _writerFactory.createWriter(out);
    }

    @Override
    public JsonWriterFactory createWriterFactory(Map<String, ?> config) {
        return new JacksonWriterFactory(config);
    }

    @Override
    public JsonReaderFactory createReaderFactory(Map<String, ?> config) {
        return new JacksonReaderFactory(config);
    }

    @Override
    public JsonObjectBuilder createObjectBuilder() {
        return _builderFactory.createObjectBuilder();
    }

    @Override
    public JsonArrayBuilder createArrayBuilder() {
        return _builderFactory.createArrayBuilder();
    }

    @Override
    public JsonBuilderFactory createBuilderFactory(Map<String, ?> config) {
        return new JacksonBuilderFactory(config);
    }

}
