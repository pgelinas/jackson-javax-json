package com.github.pgelinas.jackson.javax.json;

import java.io.*;

import javax.json.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class JacksonWriter implements JsonWriter {
    private final ObjectMapper _mapper;
    private final JsonGenerator _generator;

    public JacksonWriter(ObjectMapper mapper, Writer writer) throws IOException {
        _mapper = mapper;
        _generator = mapper.getFactory().createGenerator(writer);
    }

    public JacksonWriter(ObjectMapper mapper, OutputStream out) throws IOException {
        _mapper = mapper;
        _generator = mapper.getFactory().createGenerator(out);
    }

    @Override
    public void writeArray(JsonArray array) {
        writeValue(array);
    }

    @Override
    public void writeObject(JsonObject object) {
        writeValue(object);
    }

    @Override
    public void write(JsonStructure value) {
        writeValue(value);
    }

    private void writeValue(JsonStructure array) {
        if (!(array instanceof JacksonValue))
            throw new UnsupportedOperationException("No compatibility with other implementation yet.");
        try {
            _mapper.writeTree(_generator, ((JacksonValue<?>) array).delegate());
        } catch (IOException exception) {
            throw new JsonException("", exception);
        }
    }

    @Override
    public void close() {
        try {
            _generator.close();
        } catch (IOException exception) {
            throw new JsonException("", exception);
        }
    }
}
