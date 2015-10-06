# Scala client for the [MockServer](www.mock-server.com)

[![Build Status](https://travis-ci.org/Agord/server.svg?branch=master)](https://travis-ci.org/Unisay/mockserver-client-scala)

Implemented as a wrapper around [Java Client](https://github.com/jamesdbloom/mockserver/tree/master/mockserver-client-java)


## Examples

### Simplest case
```
always respond BadRequest
```

### One-liner with request and response bodies
```
when get "/path" has "Request body" respond Ok + "Response body"
```

### All features:

```
when post "/user" has {
  param("email", "john.doe@gmail.com") and
  param("rating", 99) and
  ContentType("application/x-www-form-urlencoded") and
  body("firstName=John&lastName=Doe")
} after 10.seconds respond {
  Accepted and Location("http://localhost:8080/user/123")
}
```

For more examples please see the test spec (DSLTest.scala)

## Installation:

```
resolvers += ("Unisay at bintray" at "http://dl.bintray.com/unisay/maven")
libraryDependencies += "com.github.unisay" %% "mockserver-client-scala" % "0.1.0" % "test"
```
