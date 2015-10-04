package com.github.unisay.mockserver.scala

import com.github.unisay.mockserver.scala.DSL.Headers._
import com.github.unisay.mockserver.scala.DSL.Statuses._
import com.github.unisay.mockserver.scala.DSL._
import org.mockserver.client.server.{ForwardChainExpectation, MockServerClient}
import org.mockserver.model.HttpRequest.{request => mockRequest}
import org.mockserver.model.HttpResponse.{response => mockResponse}
import org.mockserver.model.{HttpRequest, HttpResponse}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class DslTest extends FlatSpec with MockFactory {

  val mockServerClient = mock[NoArgMockServerClient]
  val forwardChain = mock[NoArgForwardChain]

  "Any method for any path" must "respond with status code 200" in {
    expectRequestResponse(mockRequest(), mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    always respond Ok
  }

  "Any method for any path with explicit client" must "respond with status code 200" in {
    expectRequestResponse(mockRequest(), mockResponse().withStatusCode(200))

   always(mockServerClient) respond Ok
  }

  "GET for any path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("GET"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when get *** respond Ok
  }

  "GET for any path with explicit client" must "respond with status code 200" in {
    expectRequestResponse(mockRequest().withMethod("GET"), mockResponse().withStatusCode(200))

    when(mockServerClient) get *** respond Ok
  }

  "POST for any path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("POST"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when post *** respond Ok
  }

  "GET /path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("GET").withPath("/path"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when get "/path" respond Ok
  }

  "POST /path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("POST").withPath("/path"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when post "/path" respond Ok
  }

  "PUT /path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("PUT").withPath("/path"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when put "/path" respond Ok
  }

  "DELETE /path" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest().withMethod("DELETE").withPath("/path"),
      mockResponse().withStatusCode(200))

    implicit val client = mockServerClient

    when delete "/path" respond Ok
  }

  "GET /path with one query parameter" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("GET")
        .withPath("/path")
        .withQueryStringParameter("k", "v"),
      mockResponse()
        .withStatusCode(200))

    implicit val client = mockServerClient

    when get "/path" has param("k", "v") respond Ok
  }

  "GET /path with many query parameters" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("GET")
        .withPath("/path")
        .withQueryStringParameter("k1", "v1")
        .withQueryStringParameter("k2", "v2"),
      mockResponse()
        .withStatusCode(200))

    implicit val client = mockServerClient

    when get "/path" has {
      param("k1", "v1") and param("k2", "v2")
    } respond Ok
  }

  "GET /path with one query parameter and one header" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("GET")
        .withPath("/path")
        .withQueryStringParameter("p", "1")
        .withHeader("h", "2"),
      mockResponse()
        .withStatusCode(200))

    implicit val client = mockServerClient

    when get "/path" has {
      param("p", 1) and header("h", 2)
    } respond Ok
  }

  "GET /path with many query parameters and many headers" must "respond with status code 400" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("GET")
        .withPath("/path")
        .withQueryStringParameter("p1", "pv1")
        .withQueryStringParameter("p2", "pv2")
        .withHeader("Connection", "keep-alive")
        .withHeader("Cache-Control", "no-cache"),
      mockResponse()
        .withStatusCode(400))

    implicit val client = mockServerClient

    when get "/path" has {
      param("p1", "pv1") and
        param("p2", "pv2") and
        Connection("keep-alive") and
        CacheControl("no-cache")
    } respond {
      BadRequest
    }
  }

  "GET /path with many query parameters + many headers" must "respond with status code 200" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("GET")
        .withPath("/path")
        .withQueryStringParameter("p1", "pv1")
        .withQueryStringParameter("p2", "pv2")
        .withHeader("h1", "hv1")
        .withHeader("h2", "hv2"),
      mockResponse()
        .withStatusCode(200))

    implicit val client = mockServerClient

    when get "/path" has param("p1", "pv1") + param("p2", "pv2") + header("h1", "hv1") + header("h2", "hv2") respond Ok
  }

  "GET /path with string body" must "respond with status code 200 and string body" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("POST")
        .withBody("The Request Body"),
      mockResponse()
        .withStatusCode(200)
        .withBody("The Response Body"))

    implicit val client = mockServerClient

    when post *** has "The Request Body" respond Ok + "The Response Body"
  }

  "GET /path with byte body" must "respond with status code 200 and byte body" in {
    expectRequestResponse(
      mockRequest()
        .withMethod("POST")
        .withBody("The Request Body".getBytes),
      mockResponse()
        .withStatusCode(200)
        .withBody("The Response Body".getBytes))

    implicit val client = mockServerClient

    when post *** has "The Request Body".getBytes respond Ok + "The Response Body".getBytes
  }

  class NoArgMockServerClient extends MockServerClient("localhost", 1234)

  class NoArgForwardChain extends ForwardChainExpectation(null, null)

  private def expectRequestResponse(expectedRequest: HttpRequest, expectedResponse: HttpResponse): Unit = {
    {
      mockServerClient.when(_: HttpRequest)
    }.expects(expectedRequest).returns(forwardChain)
    (forwardChain.respond _).expects(expectedResponse)
  }

}
