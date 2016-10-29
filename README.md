# Scala client for the [MockServer](www.mock-server.com)

[![Build Status](https://travis-ci.org/Agord/server.svg?branch=master)](https://travis-ci.org/Unisay/mockserver-client-scala)

Implemented as a wrapper around [Java Client](https://github.com/jamesdbloom/mockserver/tree/master/mockserver-client-java)


## Examples

### Simplest case
```scala
forAnyRequest respond BadRequest once
```

### One-liner with request and response bodies
```scala
when get "/path" has "Request body" respond Ok + "Response body" exactly 3.times
```

### All features:

```scala
when post "/user" has {
  param("email", "john.doe@gmail.com") and
  param("rating", 99) and
  ContentType("application/x-www-form-urlencoded") and
  body("firstName=John&lastName=Doe")
} after 10.seconds respond {
  Accepted and Location("http://localhost:8080/user/123")
} once
```

For more examples please see the test spec ([DSLTest.scala](/src/test/scala/com/github/unisay/mockserver/scala/DSLTest.scala))

## Installation:

```
resolvers += Resolver.bintrayRepo("unisay", "maven")
libraryDependencies += "com.github.unisay" %% "mockserver-client-scala" % "0.2.0"
```
