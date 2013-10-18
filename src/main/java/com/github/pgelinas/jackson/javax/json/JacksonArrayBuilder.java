package com.github.pgelinas.jackson.javax.json;

import java.math.*;

import javax.json.*;

import com.fasterxml.jackson.databind.node.*;

public class JacksonArrayBuilder implements JsonArrayBuilder {
    private final JsonNodeFactory _jsonNodeFactory;
    private final NodeFactory _nodeFactory;
    private final ArrayNode _delegate;

    public JacksonArrayBuilder(JsonNodeFactory jsonNodeFactory, NodeFactory nodeFactory) {
        _jsonNodeFactory = jsonNodeFactory;
        _nodeFactory = nodeFactory;
        _delegate = jsonNodeFactory.arrayNode();
    }

    public ArrayNode delegate() {
        return _delegate;
    }

    @Override
    public JsonArrayBuilder add(JsonValue value) {
        if (value == null) throw new NullPointerException();
        if (value == JsonValue.NULL) {
            _delegate.addNull();
        } else if (value == JsonValue.FALSE) {
            _delegate.add(false);
        } else if (value == JsonValue.TRUE) {
            _delegate.add(true);
        } else if (value instanceof JacksonValue) {
            _delegate.add(((JacksonValue<?>) value).delegate());
        } else {
            _delegate.add(_nodeFactory.from(value));
        }

        return this;
    }

    @Override
    public JsonArrayBuilder add(String value) {
        if (value == null) throw new NullPointerException();
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder add(BigDecimal value) {
        if (value == null) throw new NullPointerException();
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder add(BigInteger value) {
        if (value == null) throw new NullPointerException();
        _delegate.add(_jsonNodeFactory.numberNode(value));
        return this;
    }

    @Override
    public JsonArrayBuilder add(int value) {
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder add(long value) {
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder add(double value) {
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder add(boolean value) {
        _delegate.add(value);
        return this;
    }

    @Override
    public JsonArrayBuilder addNull() {
        _delegate.addNull();
        return this;
    }

    @Override
    public JsonArrayBuilder add(JsonObjectBuilder builder) {
        if (builder == null) throw new NullPointerException();
        if (!(builder instanceof JacksonObjectBuilder)) {
            _delegate.add(_nodeFactory.from(builder.build()));
        } else {
            _delegate.add(((JacksonObjectBuilder) builder).delegate());
        }
        return this;
    }

    @Override
    public JsonArrayBuilder add(JsonArrayBuilder builder) {
        if (builder == null) throw new NullPointerException();
        if (!(builder instanceof JacksonArrayBuilder)) {
            _delegate.add(_nodeFactory.from(builder.build()));
        } else {
            _delegate.add(((JacksonArrayBuilder) builder).delegate());
        }
        return this;
    }

    @Override
    public JsonArray build() {
        return new JacksonArray(_delegate.deepCopy(), _nodeFactory);
    }
}
