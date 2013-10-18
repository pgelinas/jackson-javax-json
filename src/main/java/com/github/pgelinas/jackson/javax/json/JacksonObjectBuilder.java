package com.github.pgelinas.jackson.javax.json;

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
        if (value == null || name == null) throw new NullPointerException();
        if (value == JsonValue.NULL) {
            _delegate.putNull(name);
        } else if (value == JsonValue.FALSE) {
            _delegate.put(name, false);
        } else if (value == JsonValue.TRUE) {
            _delegate.put(name, true);
        } else if (value instanceof JacksonValue) {
            _delegate.put(name, ((JacksonValue<?>) value).delegate());
        } else {
            _delegate.put(name, _nodeFactory.from(value));
        }
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, String value) {
        if (value == null || name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, BigInteger value) {
        if (value == null || name == null) throw new NullPointerException();
        _delegate.put(name, _jsonNodeFactory.numberNode(value));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, BigDecimal value) {
        if (value == null || name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, int value) {
        if (name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, long value) {
        if (name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, double value) {
        if (name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, boolean value) {
        if (name == null) throw new NullPointerException();
        _delegate.put(name, value);
        return this;
    }

    @Override
    public JsonObjectBuilder addNull(String name) {
        if (name == null) throw new NullPointerException();
        _delegate.putNull(name);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, JsonObjectBuilder builder) {
        if (builder == null || name == null) throw new NullPointerException();
        if (!(builder instanceof JacksonObjectBuilder)) {
            _delegate.put(name, _nodeFactory.from(builder.build()));
        } else {
            _delegate.put(name, ((JacksonObjectBuilder) builder).delegate());
        }
        return this;
    }

    @Override
    public JsonObjectBuilder add(String name, JsonArrayBuilder builder) {
        if (builder == null || name == null) throw new NullPointerException();
        if (!(builder instanceof JacksonArrayBuilder)) {
            _delegate.put(name, _nodeFactory.from(builder.build()));
        } else {
            _delegate.put(name, ((JacksonArrayBuilder) builder).delegate());
        }
        return this;
    }

    @Override
    public JsonObject build() {
        return new JacksonObject(_delegate.deepCopy(), _nodeFactory);
    }
}
