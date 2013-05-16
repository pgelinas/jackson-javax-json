package com.github.pgelinas.jackson.javax.json;

import java.io.*;

import javax.json.*;
import javax.json.stream.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import com.github.pgelinas.jackson.javax.json.stream.*;

public class JacksonReader implements JsonReader {
    private final ObjectMapper _mapper;
    private final NodeFactory _nodeFactory;
    private Reader _reader;
    private InputStream _in;
    private boolean _closed;

    public JacksonReader(ObjectMapper mapper, NodeFactory nodeFactory, Reader reader) {
        _mapper = mapper;
        _nodeFactory = nodeFactory;
        _reader = reader;
    }

    public JacksonReader(ObjectMapper mapper, NodeFactory nodeFactory, InputStream in) {
        _mapper = mapper;
        _nodeFactory = nodeFactory;
        _in = in;
    }

    @Override
    public JsonStructure read() {
        return (JsonStructure) read(ContainerNode.class);
    }

    @Override
    public JsonObject readObject() {
        return (JsonObject) read(ObjectNode.class);
    }

    @Override
    public JsonArray readArray() {
        return (JsonArray) read(ArrayNode.class);
    }

    private <T extends JsonNode> JsonValue read(Class<T> type) {
        if(_closed) throw new IllegalStateException();
        T node;
        try {
            if (_reader != null) {
                node = _mapper.readValue(_reader, type);
            } else {
                node = _mapper.readValue(_in, type);
            }
            _closed = true;
        } catch (JsonProcessingException exception) {
            throw new JsonParsingException("", new JacksonLocation(exception.getLocation()));
        } catch (IOException exception) {
            throw new JsonException("", exception);
        }
        return _nodeFactory.from(node);
    }

    @Override
    public void close() {
        try {
            if (_reader != null) {
                _reader.close();
            } else {
                _in.close();
            }
        } catch (IOException exception) {
            throw new JsonException("", exception);
        }
    }
}
