package com.fasterxml.jackson.javax.json;

import java.math.*;

import javax.json.*;

import com.fasterxml.jackson.databind.node.*;

public class JacksonObjectBuilder implements JsonObjectBuilder {
    private final ObjectNode _delegate;
    private final NodeFactory _nodeFactory;
    // Workaround for missing method in ContainerNode => numericNode(BigInteger)
    // Otherwise this field wouldn't be necessary.
    private final JsonNodeFactory _jsonNodeFactory;

    public JacksonObjectBuilder(JsonNodeFactory jsonNodeFactory, NodeFactory nodeFactory) {
        _jsonNodeFactory = jsonNodeFactory;
        _nodeFactory = nodeFactory;
        _delegate = jsonNodeFactory.objectNode();
    }

    public ObjectNode delegate() {
        return _delegate;
    }

    @Override
    public JsonObjectBuilder add(String name, JsonValue value) {
        if (value == JsonValue.NULL) {
            _delegate.putNull(name);
        } else if (value == JsonValue.FALSE) {
            _delegate.put(name, false);
        } else if (value == JsonValue.TRUE) {
            _delegate.put(name, true);
        } else if (value instanceof JacksonValue) {
            _delegate.put(name, ((JacksonValue<?>) value).delegate());
        } else throw new UnsupportedOperationException("No compatibility with other implementation yet.");
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, String value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, BigInteger value) {
        _delegate.put(name, _jsonNodeFactory.numberNode(value));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, BigDecimal value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, int value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, long value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, double value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, boolean value) {
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder addNull(String name) {
        _delegate.putNull(name);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, JsonObjectBuilder builder) {
        if (!(builder instanceof JacksonObjectBuilder))
            throw new UnsupportedOperationException("No compatibility with other implementation yet.");
        _delegate.put(name, ((JacksonObjectBuilder) builder).delegate());
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, JsonArrayBuilder builder) {
        if (!(builder instanceof JacksonArrayBuilder))
            throw new UnsupportedOperationException("No compatibility with other implementation yet.");
        _delegate.put(name, ((JacksonArrayBuilder) builder).delegate());
        return this;
    }

    @Override
    public JsonObject build() {
        return new JacksonObject(_delegate, _nodeFactory);
    }
}
