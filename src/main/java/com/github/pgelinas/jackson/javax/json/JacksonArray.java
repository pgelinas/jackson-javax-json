
package com.github.pgelinas.jackson.javax.json;

import java.util.*;

import javax.json.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

public class JacksonArray extends AbstractList<JsonValue> implements JsonArray, JacksonValue<ArrayNode> {
    private final NodeFactory _nodeFactory;
    private final ArrayNode _delegate;
    private List<JsonValue> _values;

    public JacksonArray(ArrayNode delegate, NodeFactory nodeFactory) {
        _delegate = delegate;
        _nodeFactory = nodeFactory;
    }

    @Override
    public int size() {
        return _delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return _delegate.size() == 0;
    }

    @Override
    public JsonObject getJsonObject(int index) {
        return (JsonObject) get(index);
    }

    @Override
    public JsonArray getJsonArray(int index) {
        return (JsonArray) get(index);
    }

    @Override
    public JsonNumber getJsonNumber(int index) {
        return (JsonNumber) get(index);
    }

    @Override
    public JsonString getJsonString(int index) {
        return (JsonString) get(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends JsonValue> List<T> getValuesAs(Class<T> clazz) {
        if (_values == null) {
            _values = new ArrayList<JsonValue>();
            for (Iterator<JsonNode> iterator = _delegate.elements(); iterator.hasNext();) {
                _values.add(_nodeFactory.from(iterator.next()));
            }
            _values = Collections.unmodifiableList(_values);
        }
        return (List<T>) _values;
    }

    @Override
    public String getString(int index) {
        return getRaw(index).asText();
    }

    @Override
    public String getString(int index, String defaultValue) {
        return getRaw(index).asText();
    }

    @Override
    public int getInt(int index) {
        return getRaw(index).asInt();
    }

    @Override
    public int getInt(int index, int defaultValue) {
        return getRaw(index).asInt(defaultValue);
    }

    @Override
    public boolean getBoolean(int index) {
        return getRaw(index).asBoolean();
    }

    @Override
    public boolean getBoolean(int index, boolean defaultValue) {
        return getRaw(index).asBoolean(defaultValue);
    }

    @Override
    public boolean isNull(int index) {
        return getRaw(index).isNull();
    }

    @Override
    public ValueType getValueType() {
        return JacksonValueUtils.getValueType(this);
    }

    @Override
    public ArrayNode delegate() {
        return _delegate;
    }

    @Override
    public JsonValue get(int index) {
        return _nodeFactory.from(getRaw(index));
    }
    
    private JsonNode getRaw(int index){
        return _delegate.get(index);
    }
    
    @Override
    public String toString() {
        return _delegate.toString();
    }
}
