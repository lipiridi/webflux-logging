# Webflux Logging

[![Maven Central](https://img.shields.io/maven-central/v/io.github.lipiridi/webflux-logging)](https://search.maven.org/artifact/io.github.lipiridi/webflux-logging)

Webflux Logging is a Java library for logging incoming HTTP requests in Spring WebFlux applications. It collects request
data into an HttpLog model and provides the capability to write this data as a JSON string to the console. The library
offers additional configuration options and extensibility through the use of custom HttpLogConsumer beans.

## Features

- **Automatic Logging:** By default, the library is enabled and will log incoming requests automatically.

- **Customizable Logging:** You can configure the library to ignore specific request patterns using the
  `logging.webflux.http.ignore-patterns` application property. This property accepts a list of string patterns, and
  matching
  is done via the AntPathMatcher.

- **Extensibility:** The library provides an interface named HttpLogConsumer, which allows you to register custom beans
  that
  consume HttpLog objects. This feature enables you to perform additional actions with the request data.

## Getting Started

### Installation

To include this library in your Spring WebFlux project, add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.lipiridi</groupId>
    <artifactId>webflux-logging</artifactId>
    <version>1.0.0</version> <!-- Replace with the latest version -->
</dependency>
```

### Configuration

By default, the library is enabled. However, you can disable it by specifying the following application property in your
`application.properties` or `application.yml`:

```properties
logging.webflux.http.enabled=false
```

#### Ignoring Patterns

To specify patterns that should be ignored for logging, use the `logging.webflux.http.ignore-patterns` application
property. Provide a list of string patterns to match against incoming requests.

```properties
logging.webflux.http.ignore-patterns=/actuator/**, /swagger-ui
```

### Custom HttpLog Consumers

To perform additional actions with `HttpLog` objects, you can implement the `HttpLogConsumer` interface and register it
as a bean. Here's an example of creating a custom `HttpLogConsumer`:
```java
import org.springframework.stereotype.Component;

@Component
public class CustomHttpLogConsumer implements HttpLogConsumer {
    @Override
    public void accept(HttpLog httpLog) {
        // Custom actions with the HttpLog object
    }
}
```

## Sample HttpLog Output
Here's an example of the JSON output that the library generates for an HTTP request:
```json
{
    "uri": "http://localhost:8080/ping?filter=123",
    "path": "/ping",
    "method": "POST",
    "statusCode": 200,
    "queryParams": {
        "filter": ["123"]
    },
    "formData": {},
    "requestHeaders": {
        // Request headers here
    },
    "requestBody": {
        "test": "Hello Webflux!"
    },
    "responseHeaders": {
        // Response headers here
    },
    "responseBody": {
        "result": "ok"
    }
}
```

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
