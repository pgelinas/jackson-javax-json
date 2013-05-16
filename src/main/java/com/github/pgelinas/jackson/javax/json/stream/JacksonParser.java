package com.github.pgelinas.jackson.javax.json.stream;

import static com.fasterxml.jackson.core.JsonToken.*;

import java.io.*;
import java.math.*;
import java.util.*;

import javax.json.*;
import javax.json.stream.*;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;

import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.*;

public class JacksonParser implements JsonParser {

    private static EnumMap<JsonToken, Event> _tokenToEvent;

    static {
        _tokenToEvent = new EnumMap<JsonToken, Event>(JsonToken.class);
        _tokenToEvent.put(JsonToken.END_ARRAY, Event.END_ARRAY);
        _tokenToEvent.put(JsonToken.END_OBJECT, Event.END_OBJECT);
        _tokenToEvent.put(JsonToken.FIELD_NAME, Event.KEY_NAME);
        _tokenToEvent.put(JsonToken.START_ARRAY, Event.START_ARRAY);
        _tokenToEvent.put(JsonToken.START_OBJECT, Event.START_OBJECT);
        _tokenToEvent.put(JsonToken.VALUE_FALSE, Event.VALUE_FALSE);
        _tokenToEvent.put(JsonToken.VALUE_NULL, Event.VALUE_NULL);
        _tokenToEvent.put(JsonToken.VALUE_NUMBER_FLOAT, Event.VALUE_NUMBER);
        _tokenToEvent.put(JsonToken.VALUE_NUMBER_INT, Event.VALUE_NUMBER);
        _tokenToEvent.put(JsonToken.VALUE_STRING, Event.VALUE_STRING);
        _tokenToEvent.put(JsonToken.VALUE_TRUE, Event.VALUE_TRUE);
    }

    private com.fasterxml.jackson.core.JsonParser _parser;
    private boolean _hasNextCalled;
    private JsonToken _nextToken;

    public JacksonParser(com.fasterxml.jackson.core.JsonParser parser) {
        _parser = parser;
    }

    @Override
    public boolean hasNext() {
        if (!_hasNextCalled) {
            advanceParser();
            _hasNextCalled = true;
        }
        return _tokenToEvent.containsKey(_nextToken);
    }

    @Override
    public Event next() {
        if (!_hasNextCalled) {
            advanceParser();
        }
        _hasNextCalled = false;
        Event event = _tokenToEvent.get(_nextToken);
        if (event == null) throw new NoSuchElementException();
        return event;
    }

    private void advanceParser() {
        try {
            _nextToken = _parser.nextToken();
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public String getString() {
        JsonToken currentToken = _parser.getCurrentToken();
        try {
            if (currentToken == FIELD_NAME) {
                return _parser.getCurrentName();
            } else if (currentToken == VALUE_STRING
                       || currentToken == VALUE_NUMBER_FLOAT
                       || currentToken == VALUE_NUMBER_INT) { return _parser.getValueAsString(); }
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
        throw new IllegalStateException("Illegal parser state for getString()");
    }

    @Override
    public boolean isIntegralNumber() {
        JsonToken currentToken = _parser.getCurrentToken();
        if(currentToken != JsonToken.VALUE_NUMBER_FLOAT && currentToken != JsonToken.VALUE_NUMBER_INT)
            throw new IllegalStateException("Illegal parser state for isIntegralNumber()");
        try {
            return _parser.getNumberType() == NumberType.INT;
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public int getInt() {
        JsonToken currentToken = _parser.getCurrentToken();
        if(currentToken != JsonToken.VALUE_NUMBER_FLOAT && currentToken != JsonToken.VALUE_NUMBER_INT)
            throw new IllegalStateException("Illegal parser state for isIntegralNumber()");
        try {
            return _parser.getIntValue();
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public long getLong() {
        JsonToken currentToken = _parser.getCurrentToken();
        if(currentToken != JsonToken.VALUE_NUMBER_FLOAT && currentToken != JsonToken.VALUE_NUMBER_INT)
            throw new IllegalStateException("Illegal parser state for isIntegralNumber()");
        try {
            return _parser.getLongValue();
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public BigDecimal getBigDecimal() {
        JsonToken currentToken = _parser.getCurrentToken();
        if(currentToken != JsonToken.VALUE_NUMBER_FLOAT && currentToken != JsonToken.VALUE_NUMBER_INT)
            throw new IllegalStateException("Illegal parser state for isIntegralNumber()");
        try {
            return _parser.getDecimalValue();
        } catch (JsonParseException e){
            throw new JsonParsingException("", e, getLocation());
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }

    @Override
    public JsonLocation getLocation() {
        com.fasterxml.jackson.core.JsonLocation location = _parser.getCurrentLocation();
        return new JacksonLocation(location);

    }

    @Override
    public void close() {
        try {
            _parser.close();
        } catch (IOException e) {
            throw new JsonException("", e);
        }
    }
}
