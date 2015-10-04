# Scala client for the [MockServer](www.mock-server.com)

Implemented as a wrapper around [Java Client](https://github.com/jamesdbloom/mockserver/tree/master/mockserver-client-java)


Some examples:
```
always respond BadRequest
```

```
when get "/path" has "Request body" respond Ok + "Response body"
```

```
when post "/user" has {
  param("email", "john.doe@gmail.com") and
  param("rating", 99) and
  ContentType("application/x-www-form-urlencoded") and
  body("firstName=John&lastName=Doe")
} respond {
  Accepted and Location("http://localhost:8080/user/123")
}
```
