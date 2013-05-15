 /*
  * Copyright (c) 2002-2013 Nu Echo Inc.  All rights reserved. 
  */

package com.github.pgelinas.jackson.javax.json;

import static org.junit.Assert.*;

import java.io.*;

import javax.json.*;
import javax.json.spi.*;

import org.junit.*;

import com.github.pgelinas.jackson.javax.json.spi.*;

/**
 * @author Nu Echo Inc.
 */
public class TreeModelTest {
    private static final JsonProvider _provider = new JacksonProvider();
    @Test
    public void test() {
        InputStream in = getClass().getResourceAsStream("object.json");
        JsonReader reader = _provider.createReader(in);
        JsonObject object = reader.readObject();
        assertTrue(object.containsKey("field"));
    }
}
