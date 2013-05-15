package com.github.pgelinas.jackson.javax.json;

import java.math.*;

import javax.json.*;

import com.fasterxml.jackson.databind.node.*;

public class JacksonNumber extends JacksonValueNode<NumericNode> implements JsonNumber {
    public JacksonNumber(NumericNode node) {
        super(node);
    }

    @Override
    public boolean isIntegral() {
        return _delegate.isIntegralNumber();
    }

    @Override
    public int intValue() {
        return _delegate.intValue();
    }

    @Override
    public int intValueExact() {
        if(!_delegate.isInt()) throw new ArithmeticException();
        return _delegate.intValue();
    }

    @Override
    public long longValue() {
        return _delegate.longValue();
    }

    @Override
    public long longValueExact() {
        if(!_delegate.isLong() && !_delegate.isInt()) throw new ArithmeticException();
        return _delegate.longValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return _delegate.bigIntegerValue();
    }

    @Override
    public BigInteger bigIntegerValueExact() {
        if(!_delegate.isIntegralNumber()) throw new ArithmeticException();
        return _delegate.bigIntegerValue();
    }

    @Override
    public double doubleValue() {
        return _delegate.doubleValue();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        return _delegate.decimalValue();
    }
}
