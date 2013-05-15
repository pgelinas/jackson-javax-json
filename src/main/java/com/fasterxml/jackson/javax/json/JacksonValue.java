package com.fasterxml.jackson.javax.json;

import javax.json.*;

import com.fasterxml.jackson.core.*;

public interface JacksonValue<T extends TreeNode> extends JsonValue {
    T delegate();
}
