package com.github.pgelinas.jackson.javax.json;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import javax.json.*;

import com.fasterxml.jackson.databind.*;

public class JacksonReaderFactory implements JsonReaderFactory {
    private final ObjectMapper _mapper;
    private final NodeFactory _nodeFactory;
    private Map<String, Object> _configInUse;

    public JacksonReaderFactory(Map<String, ?> config) {
        _mapper = new ObjectMapper();
        _nodeFactory = new NodeFactory(_mapper.getNodeFactory());
        _configInUse = ConfigurationUtils.configure(_mapper, config);
    }

    public JacksonReaderFactory(ObjectMapper mapper, NodeFactory nodeFactory) {
        _mapper = mapper;
        _nodeFactory = nodeFactory;
    }

    @Override
    public JsonReader createReader(Reader reader) {
        return new JacksonReader(_mapper, _nodeFactory, reader);
    }

    @Override
    public JsonReader createReader(InputStream in) {
        return new JacksonReader(_mapper, _nodeFactory, in);
    }

    @Override
    public JsonReader createReader(InputStream in, Charset charset) {
        return createReader(new InputStreamReader(in, charset));
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return _configInUse;
    }
}
