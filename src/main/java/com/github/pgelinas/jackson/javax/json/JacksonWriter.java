package com.github.pgelinas.jackson.javax.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pgelinas.jackson.javax.json.stream.JacksonGenerator;

public class JacksonWriter implements JsonWriter {
    private final ObjectMapper _mapper;
    private final JacksonGenerator _generator;

    public JacksonWriter(ObjectMapper mapper, Writer writer) throws IOException {
        _mapper = mapper;
        _generator = new JacksonGenerator(mapper.getFactory().createGenerator(writer));
    }

    public JacksonWriter(ObjectMapper mapper, OutputStream out) throws IOException {
        _mapper = mapper;
        _generator = new JacksonGenerator(mapper.getFactory().createGenerator(out));
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

    private void writeValue(JsonStructure structure) {
        if (structure instanceof JacksonValue) {
            try {
                _mapper.writeTree(_generator.delegate(), ((JacksonValue<?>) structure).delegate());
            } catch (IOException exception) {
                throw new JsonException("", exception);
            }
        } else {
            _generator.write(structure);
        }
    }

    @Override
    public void close() {
        _generator.close();
    }
}
