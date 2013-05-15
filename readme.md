# Overview

This is an alternate implementation of [JSR353](http://jcp.org/en/jsr/detail?id=353), the reference implementation being from the [Glassfish project](https://jsonp.java.net/). It aims at providing Jackson as a drop-in replacement for the RI, which brings better performance and configurability.

## Building

This project uses [Gradle](http://www.gradle.org/) for its build system and has an helpful wrapper to bootstrap. At the root of the project, run `gradlew build` to build the project.

## Developing

Gradle has support for both Eclipse and IDEA project metadata generation; the project has added Eclipse support. Run `gradlew eclipse` to generate metadata, then import in Eclipse as an existing project.