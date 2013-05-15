package com.github.pgelinas.jackson.javax.json;

import com.fasterxml.jackson.databind.node.*;

public class JacksonValueNode<T extends ValueNode> implements JacksonValue<T> {
    protected T _delegate;

    public JacksonValueNode(T delegate) {
        _delegate = delegate;
    }

    @Override
    public ValueType getValueType() {
        return JacksonValueUtils.getValueType(this);
    }

    @Override
    public T delegate() {
        return _delegate;
    }

    public final int hashCode() {
        return _delegate.hashCode();
    }
    
    @Override
    public final boolean equals(Object obj) {
        return JacksonValueUtils.isEquals(this, obj);
    }
    
    @Override
    public final String toString() {
        return _delegate.toString();
    }
}
