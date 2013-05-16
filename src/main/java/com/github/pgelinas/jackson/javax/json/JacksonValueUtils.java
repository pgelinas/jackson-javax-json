package com.github.pgelinas.jackson.javax.json;

import java.util.*;

import javax.json.JsonValue.ValueType;

import com.fasterxml.jackson.core.*;

public final class JacksonValueUtils {
    private JacksonValueUtils() {}
    
    private static EnumMap<JsonToken, ValueType> _tokenToValueType;

    static {
        _tokenToValueType = new EnumMap<JsonToken, ValueType>(JsonToken.class);
        _tokenToValueType.put(JsonToken.START_ARRAY, ValueType.ARRAY);
        _tokenToValueType.put(JsonToken.START_OBJECT, ValueType.OBJECT);
        _tokenToValueType.put(JsonToken.VALUE_FALSE, ValueType.FALSE);
        _tokenToValueType.put(JsonToken.VALUE_TRUE, ValueType.TRUE);
        _tokenToValueType.put(JsonToken.VALUE_NULL, ValueType.NULL);
        _tokenToValueType.put(JsonToken.VALUE_NUMBER_FLOAT, ValueType.NUMBER);
        _tokenToValueType.put(JsonToken.VALUE_NUMBER_INT, ValueType.NUMBER);
        _tokenToValueType.put(JsonToken.VALUE_STRING, ValueType.STRING);
    }
    
    public static ValueType getValueType(JacksonValue<?> value) {
        return _tokenToValueType.get(value.delegate().asToken());
    }

    protected static boolean isEquals(JacksonValue<?> thisValue, Object obj) {
        if (thisValue == obj) return true;
        if (obj == null) return false;
        if (thisValue.getClass() != obj.getClass()) return false;
        JacksonValue<?> other = (JacksonValue<?>) obj;
        if (thisValue.delegate() == null) {
            if (other.delegate() != null) return false;
        } else if (!thisValue.delegate().equals(other.delegate())) return false;
        return true;
    }
    
    
}
