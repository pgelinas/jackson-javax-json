# I am no longer supporting this project, you are free to fork and modify as you wish.

# Overview

This is an alternate implementation of [JSR353](http://jcp.org/en/jsr/detail?id=353), the reference implementation being from the [Glassfish project](https://jsonp.java.net/). It aims at providing Jackson as a drop-in replacement for the RI, which brings better performance and configurability.

## Configuration

The JSR api allows for configuration of the underlying implementation through its factory method on `JsonProvider`; these methods take a `Map<String,?>` as an argument which contains the configuration. In this case, the keys of the map are the Features enum name and the values are boolean. 

```Java
JsonProvider provider = JsonProvider.provider();
Map<String, Boolean> config = new HashMap<String, Boolean>();
config.put(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.name(), true);
config.put(JsonParser.Feature.ALLOW_COMMENTS.name(), true);
config.put(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT.name(), true);
provider.createParserFactory(config); // Understands JsonFactory and JsonParser features
provider.createGeneratorFactory(config); // Understands JsonFactory and JsonGenerator features

config.put(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS.name(), true);
config.put(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT.name(), true);
config.put(SerializationFeature.CLOSE_CLOSEABLE.name(), true);
provider.createReaderFactory(config); // Understands JsonFactory, JsonParser, Mapper and Deserialization features
provider.createWriterFactory(config); // Understands JsonFactory, JsonGenerator, Mapper and Serialization features
provider.createBuilderFactory(Collections.emptyMap()); // No configuration needed
``` 

Of course, you don't have to directly reference the Jackson enum class, you can put directly the enum name in the map and not depend on Jackson in your project.

## Tests

As you can see, there's not much unit tests that come bundled with the project. The unit tests used are the ones from the RI project to ensure that this project respects the API. Some of the tests are failing and probably won't be fixed; see the [differences section](#differences-from-the-api) for an explanation.  

## Differences from the API

Some of the API isn't respected by this library for various reason. Some of this is highlighted by failing unit tests, some are deliberate omission. Here's the rundown:

* `JsonValue` class tree implementation are NOT immutable, you simply don't have access to mutator methods. This mirror the fact that Jackson's `TreeNode` implementation isn't immutable and you only have access to accessor method on the interface.
* Failing tests
    * `JsonParsingExceptionTest#testWrongJson` (and `testWrongJson1`): the API is too restrictive about JSON correctness. These cases are useful when receiving a continuous stream of data from a server, which can sends information object by object.
    * `JssonParsingExceptionTest#testLocation1`: when Jackson throws an exception about an unrecognized token it does so with a location that points at the END of the token; the RI does it with a location that points at an offending character.
    * `JsonNumberTest#testBigDecimal` (and `testIntNumberType`): the API specifies number operation as counterparts of `BigDecimal` and the RI always uses BigDecimal to store its data, even if it would fit in a `short`. This has performance implication and equality implication too; the failures are explained because some nodes are numerically equals but have a different class.
    * `JsonGeneratorTest`
        * `testGenerationException3`: closing a writer which as written nothing isn't a problem usually, but this API says otherwise. Simply ignore this test.
        * `testGenerationException4` and `testGenerationException5`: when closing its generator, Jackson automatically closes any open object/array, if it will generate correct JSON. 
        * `testGenerationArrayDouble` and `testGenerationObjectDouble`: Jackson is able to handle `NaN` and inifity by writing the value as a String instead and reads it in the same way.

## Building

This project uses [Gradle](http://www.gradle.org/) for its build system and has an helpful wrapper to bootstrap. At the root of the project, run `gradlew build` to build the project.

## Usage

Add a dependency on this project with your favorite dependency management tool (at optional/provided/runtime, whatever suits you) and add a file named `javax.json.spi.JsonProvider` with the line `com.github.pgelinas.jackson.javax.json.spi.JacksonProvider` in `META-INF/services/. This is the usual mechanism for Java's SPI.

## Developing

Gradle has support for both Eclipse and IDEA project metadata generation; the project has added Eclipse support. Run `gradlew eclipse` to generate metadata, then import in Eclipse as an existing project.
