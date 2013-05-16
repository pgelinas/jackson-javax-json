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

Of course, you don't have to the directly reference the Jackson enum class, you can put directly the enum name in the map and not depend on Jackson in your project.

## Building

This project uses [Gradle](http://www.gradle.org/) for its build system and has an helpful wrapper to bootstrap. At the root of the project, run `gradlew build` to build the project.

## Developing

Gradle has support for both Eclipse and IDEA project metadata generation; the project has added Eclipse support. Run `gradlew eclipse` to generate metadata, then import in Eclipse as an existing project.