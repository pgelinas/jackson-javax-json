package com.github.pgelinas.jackson.javax.json.stream;

import javax.json.stream.*;

public class JacksonLocation implements JsonLocation {

    private com.fasterxml.jackson.core.JsonLocation _location;

    public JacksonLocation(com.fasterxml.jackson.core.JsonLocation location) {
        _location = location;
    }

    @Override
    public long getLineNumber() {
        return _location.getLineNr();
    }

    @Override
    public long getColumnNumber() {
        return _location.getColumnNr();
    }

    @Override
    public long getStreamOffset() {
        long byteOffset = _location.getByteOffset();
        return byteOffset == -1 ? _location.getCharOffset() : byteOffset;
    }

}
