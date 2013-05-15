package com.github.pgelinas.jackson.javax.json;

import java.util.*;
import java.util.Map.Entry;

import javax.json.*;

import com.fasterxml.jackson.databind.*;

public final class JacksonEntry implements Entry<String, JsonValue> {
    private final Entry<String, JsonNode> _entry;
    private final NodeFactory _nodeFactory;

    public JacksonEntry(Map.Entry<String, JsonNode> entry, NodeFactory nodeFactory) {
        _entry = entry;
        _nodeFactory = nodeFactory;
    }

    @Override
    public String getKey() {
        return _entry.getKey();
    }

    @Override
    public JsonValue getValue() {
        return _nodeFactory.from(_entry.getValue());
    }

    @Override
    public JsonValue setValue(JsonValue value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_entry == null) ? 0 : _entry.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        JacksonEntry other = (JacksonEntry) obj;
        if (_entry == null) {
            if (other._entry != null) return false;
        } else if (!_entry.equals(other._entry)) return false;
        return true;
    }
}
