package com.github.pgelinas.jackson.javax.json;

import org.junit.Test;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReadPropertiesWithDefaultValuesTest {

    private static final JsonProvider PROVIDER = JsonProvider.provider();

    private static final JsonObject EMPTY_JSON_OBJECT = PROVIDER.createReader(new StringReader("{}")).readObject();

    @Test
    public void getStringPropertyWithDefaultValue() {
        assertThat(EMPTY_JSON_OBJECT.getString("prop", "default"), equalTo("default"));
    }

    @Test
    public void getBooleanPropertyWithDefaultValue() {
        assertThat(EMPTY_JSON_OBJECT.getBoolean("prop", true), equalTo(true));
    }

    @Test
    public void getIntPropertyWithDefaultValue() {
        assertThat(EMPTY_JSON_OBJECT.getInt("prop", 100), equalTo(100));
    }

}
