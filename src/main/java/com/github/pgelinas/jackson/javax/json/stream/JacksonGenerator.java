package com.github.pgelinas.jackson.javax.json.stream;

import java.io.*;
import java.math.*;
import java.util.Map.Entry;

import javax.json.*;
import javax.json.JsonValue.ValueType;
import javax.json.stream.JsonGenerationException;
import javax.json.stream.JsonGenerator;

import com.fasterxml.jackson.core.*;
import com.github.pgelinas.jackson.javax.json.*;

public class JacksonGenerator implements JsonGenerator {
    private final com.fasterxml.jackson.core.JsonGenerator _generator;

    public JacksonGenerator(com.fasterxml.jackson.core.JsonGenerator generator) {
        _generator = generator;
    }

    @Override
    public JsonGenerator writeStartObject() {
        try {
            _generator.writeStartObject();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartObject(String name) {
        try {
            _generator.writeFieldName(name);
            _generator.writeStartObject();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartArray() {
        try {
            _generator.writeStartArray();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeStartArray(String name) {
        try {
            _generator.writeFieldName(name);
            _generator.writeStartArray();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, JsonValue value) {
        if (value == null) throw new NullPointerException();

        // Fast track, implementation is Jackson-backed.
        if (value instanceof JacksonValue) {
            try {
                _generator.writeFieldName(name);
                _generator.writeTree(((JacksonValue<?>) value).delegate());
            } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
                throw new JsonGenerationException("", e);
            } catch (IOException e) {
                throw new JsonException("", e);
            }
            return this;
        }

        // Slower track, implementation is not backed by Jackson and we need to generate json manually.
        ValueType type = value.getValueType();
        switch (type) {
            case NUMBER:
                write(name, ((JsonNumber) value).bigDecimalValue());
                break;
            case STRING:
                write(name, ((JsonString) value).getString());
                break;
            case NULL:
                writeNull(name);
                break;
            case FALSE:
                write(name, false);
                break;
            case TRUE:
                write(name, true);
                break;
            case ARRAY:
                writeStartArray(name);
                writeArray((JsonArray) value);
                writeEnd();
                break;
            case OBJECT:
                writeStartObject(name);
                writeObject((JsonObject) value);
                writeEnd();
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return this;
    }

    @Override
    public JsonGenerator write(String name, String value) {
        try {
            _generator.writeStringField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, BigInteger value) {
        try {
            _generator.writeFieldName(name);
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, BigDecimal value) {
        try {
            _generator.writeNumberField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, int value) {
        try {
            _generator.writeNumberField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, long value) {
        try {
            _generator.writeNumberField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, double value) {
        try {
            _generator.writeNumberField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(String name, boolean value) {
        try {
            _generator.writeBooleanField(name, value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeNull(String name) {
        try {
            _generator.writeNullField(name);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeEnd() {
        JsonStreamContext context = _generator.getOutputContext();
        try {
            if (context.inObject()) {
                _generator.writeEndObject();
            } else if (context.inArray()) {
                _generator.writeEndArray();
            } else {
                throw new JsonGenerationException("No end marker to write for root-level value.");
            }
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(JsonValue value) {
        if (value == null) throw new NullPointerException();

        // Fast track, implementation is Jackson-backed.
        if (value instanceof JacksonValue) {
            try {
                _generator.writeTree(((JacksonValue<?>) value).delegate());
            } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
                throw new JsonGenerationException("", e);
            } catch (IOException e) {
                throw new JsonException("", e);
            }
            return this;
        }

        // Slower track, implementation is not backed by Jackson and we need to generate json manually.
        ValueType type = value.getValueType();
        switch (type) {
            case NUMBER:
                write(((JsonNumber) value).bigDecimalValue());
                break;
            case STRING:
                write(((JsonString) value).getString());
                break;
            case NULL:
                writeNull();
                break;
            case FALSE:
                write(false);
                break;
            case TRUE:
                write(true);
                break;
            case ARRAY:
                writeStartArray();
                writeArray((JsonArray) value);
                writeEnd();
                break;
            case OBJECT:
                writeStartObject();
                writeObject((JsonObject) value);
                writeEnd();
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return this;

    }

    private void writeObject(JsonObject value) {
        for (Entry<String, JsonValue> entry : value.entrySet()) {
            write(entry.getKey(), entry.getValue());
        }
    }

    private void writeArray(JsonArray value) {
        for (JsonValue jsonValue : value) {
            write(jsonValue);
        }
    }

    @Override
    public JsonGenerator write(String value) {
        try {
            _generator.writeString(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(BigDecimal value) {
        try {
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(BigInteger value) {
        try {
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(int value) {
        try {
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(long value) {
        try {
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(double value) {
        try {
            _generator.writeNumber(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator write(boolean value) {
        try {
            _generator.writeBoolean(value);
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public JsonGenerator writeNull() {
        try {
            _generator.writeNull();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        return this;
    }

    @Override
    public void close() {
        try {
            _generator.close();
        } catch (com.fasterxml.jackson.core.JsonGenerationException e) {
            throw new JsonGenerationException("", e);
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public void flush() {
        try {
            _generator.flush();
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    public com.fasterxml.jackson.core.JsonGenerator delegate() {
        return _generator;
    }
}
