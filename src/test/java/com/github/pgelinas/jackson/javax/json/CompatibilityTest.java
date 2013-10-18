package com.github.pgelinas.jackson.javax.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;

import org.junit.Test;

public class CompatibilityTest {

    private static final JsonProvider PROVIDER = JsonProvider.provider();

    @Test
    public void handleArrayWithArbitraryImpl() throws Exception {
        JsonString jsonString = new MyJsonString("zeString");
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = PROVIDER.createWriter(writer);
        JsonArrayBuilder array = PROVIDER.createArrayBuilder().add(jsonString);
        jsonWriter.write(array.build());
        jsonWriter.close();
        assertThat(writer.toString(), equalTo("[\"zeString\"]"));
    }

    @Test
    public void handleObjectWithArbitraryImpl() throws Exception {
        JsonString jsonString = new MyJsonString("zeString");
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = PROVIDER.createWriter(writer);
        JsonObjectBuilder object = PROVIDER.createObjectBuilder().add("zeName", jsonString);
        jsonWriter.write(object.build());
        jsonWriter.close();
        assertThat(writer.toString(), equalTo("{\"zeName\":\"zeString\"}"));
    }

    @Test
    public void handleArbitraryArrayImpl() throws Exception {
        List<JsonValue> data = new ArrayList<JsonValue>();
        MyJsonArray myJsonArray = new MyJsonArray(data);
        data.add(new MyJsonString("zeString"));
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = PROVIDER.createWriter(writer);
        jsonWriter.write(myJsonArray);
        jsonWriter.close();
        assertThat(writer.toString(), equalTo("[\"zeString\"]"));
    }
    
    public static class MyJsonString implements JsonString {
        private String _zeString;

        public MyJsonString(String zeString) {
            this._zeString = zeString;
        }

        @Override
        public ValueType getValueType() {
            return ValueType.STRING;
        }

        @Override
        public String getString() {
            return _zeString;
        }

        @Override
        public CharSequence getChars() {
            return _zeString;
        }
    }
    
    // Test impl, functionnal for the test case but otherwise not complete.
    public static class MyJsonArray implements JsonArray{
        private final List<JsonValue> _data;
        
        public MyJsonArray(List<JsonValue> data) {
            _data = data;
        }

        public int size() {
            return _data.size();
        }

        public boolean isEmpty() {
            return _data.isEmpty();
        }

        public boolean contains(Object o) {
            return _data.contains(o);
        }

        public Iterator<JsonValue> iterator() {
            return _data.iterator();
        }

        public Object[] toArray() {
            return _data.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return _data.toArray(a);
        }

        public boolean add(JsonValue e) {
            return _data.add(e);
        }

        public boolean remove(Object o) {
            return _data.remove(o);
        }

        public boolean containsAll(Collection<?> c) {
            return _data.containsAll(c);
        }

        public boolean addAll(Collection<? extends JsonValue> c) {
            return _data.addAll(c);
        }

        public boolean addAll(int index, Collection<? extends JsonValue> c) {
            return _data.addAll(index, c);
        }

        public boolean removeAll(Collection<?> c) {
            return _data.removeAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return _data.retainAll(c);
        }

        public void clear() {
            _data.clear();
        }

        public boolean equals(Object o) {
            return _data.equals(o);
        }

        public int hashCode() {
            return _data.hashCode();
        }

        public JsonValue get(int index) {
            return _data.get(index);
        }

        public JsonValue set(int index, JsonValue element) {
            return _data.set(index, element);
        }

        public void add(int index, JsonValue element) {
            _data.add(index, element);
        }

        public JsonValue remove(int index) {
            return _data.remove(index);
        }

        public int indexOf(Object o) {
            return _data.indexOf(o);
        }

        public int lastIndexOf(Object o) {
            return _data.lastIndexOf(o);
        }

        public ListIterator<JsonValue> listIterator() {
            return _data.listIterator();
        }

        public ListIterator<JsonValue> listIterator(int index) {
            return _data.listIterator(index);
        }

        public List<JsonValue> subList(int fromIndex, int toIndex) {
            return _data.subList(fromIndex, toIndex);
        }

        @Override
        public ValueType getValueType() {
            return ValueType.ARRAY;
        }

        @Override
        public JsonObject getJsonObject(int index) {
            return (JsonObject) get(index);
        }

        @Override
        public JsonArray getJsonArray(int index) {
            return (JsonArray) get(index);
        }

        @Override
        public JsonNumber getJsonNumber(int index) {
            return (JsonNumber) get(index);
        }

        @Override
        public JsonString getJsonString(int index) {
            return (JsonString) get(index);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends JsonValue> List<T> getValuesAs(Class<T> clazz) {
            return (List<T>) _data;
        }

        @Override
        public String getString(int index) {
            return getJsonString(index).getString();
        }

        @Override
        public String getString(int index, String defaultValue) {
            return defaultValue;
        }

        @Override
        public int getInt(int index) {
            return getJsonNumber(index).intValue();
        }

        @Override
        public int getInt(int index, int defaultValue) {
            return defaultValue;
        }

        @Override
        public boolean getBoolean(int index) {
            return get(index) == JsonValue.TRUE;
        }

        @Override
        public boolean getBoolean(int index, boolean defaultValue) {
            return defaultValue;
        }

        @Override
        public boolean isNull(int index) {
            return get(index) == null;
        }
    }
}
