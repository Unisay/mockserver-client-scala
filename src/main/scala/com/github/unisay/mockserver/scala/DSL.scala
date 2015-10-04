package com.github.unisay.mockserver.scala

import java.nio.charset.Charset

import org.mockserver.client.server.MockServerClient
import org.mockserver.model.HttpRequest.{request => mockRequest}
import org.mockserver.model.HttpResponse.{response => mockResponse}
import org.mockserver.model.{HttpRequest => MockRequest, HttpResponse => MockResponse}

import scala.language.implicitConversions

object DSL {

  implicit def string2body(string: String): body = body(string)

  implicit def bytes2body(array: Array[Byte]): bytes = bytes(array)

  sealed trait RequestModifier {
    def +(modifier: RequestModifier): RequestModifier = and(modifier)

    def and(modifier: RequestModifier): CompositeRequestModifier = CompositeRequestModifier(this, modifier)

    def apply(mockRequest: MockRequest): MockRequest
  }

  case class CompositeRequestModifier(requestModifiers: RequestModifier*) extends RequestModifier {
    override def apply(request: MockRequest): MockRequest =
      requestModifiers.foldLeft(request)((request, modifier) => modifier(request))
  }

  sealed trait ResponseModifier {
    def +(modifier: ResponseModifier): ResponseModifier = and(modifier)

    def and(modifier: ResponseModifier): CompositeResponseModifier = CompositeResponseModifier(this, modifier)

    def apply(mockResponse: MockResponse): MockResponse
  }

  case class CompositeResponseModifier(responseModifiers: ResponseModifier*) extends ResponseModifier {
    override def apply(response: MockResponse): MockResponse =
      responseModifiers.foldLeft(response)((response, modifier) => modifier(response))
  }

  class ExpectationBuilder(val requestModifier: RequestModifier) {
    def has(additionalRequestModifier: RequestModifier): ExpectationBuilder =
      new ExpectationBuilder(requestModifier and additionalRequestModifier)

    def respond(responseModifier: ResponseModifier)(implicit mockServerClient: MockServerClient): Unit =
      mockServerClient.when(requestModifier(mockRequest)).respond(responseModifier(mockResponse))
  }

  case object GET extends RequestModifier {
    override def apply(request: MockRequest): MockRequest = request.withMethod("GET")
  }

  case object POST extends RequestModifier {
    override def apply(request: MockRequest): MockRequest = request.withMethod("POST")
  }

  case class path(str: String) extends RequestModifier {
    override def apply(request: MockRequest): MockRequest = request.withPath(str)
  }

  case class param(name: String, value: Any) extends RequestModifier {
    override def apply(request: MockRequest): MockRequest = request.withQueryStringParameter(name, value.toString)
  }

  case class header(name: String, value: Any) extends RequestModifier with ResponseModifier {
    override def apply(request: MockRequest): MockRequest =
      request.withHeader(name, value.toString)

    override def apply(response: MockResponse): MockResponse =
      response.withHeader(name, value.toString)
  }

  case class status(code: Int) extends ResponseModifier {
    override def apply(response: MockResponse): MockResponse = response.withStatusCode(code)
  }

  case class body(body: String, maybeCharset: Option[Charset] = None) extends RequestModifier with ResponseModifier {
    override def apply(request: MockRequest): MockRequest =
      if (maybeCharset.isDefined)
        request.withBody(body, maybeCharset.get)
      else
        request.withBody(body)

    override def apply(response: MockResponse): MockResponse =
      response.withBody(body)
  }

  case class bytes(bytes: Array[Byte]) extends RequestModifier with ResponseModifier {
    override def apply(request: MockRequest): MockRequest =
      request.withBody(bytes)

    override def apply(response: MockResponse): MockResponse =
      response.withBody(bytes)
  }

  val *** = ""
  val anyPath = ***

  object when {
    def get(pathStr: String): ExpectationBuilder = new ExpectationBuilder(GET + path(pathStr))

    def post(pathStr: String): ExpectationBuilder = new ExpectationBuilder(POST + path(pathStr))
  }

  object always extends ExpectationBuilder(CompositeRequestModifier())

  object Statuses {
    val Ok = status(200)
    val Created = status(201)
    val Accepted = status(202)
    val NonAuthoritativeInformation = status(203)
    val NoContent = status(204)
    val ResetContent = status(205)
    val PartialContent = status(206)

    val MultipleChoices = status(300)
    val MovedPermanently = status(301)
    val Found = status(302)
    val SeeOther = status(303)
    val NotModified = status(304)
    val UseProxy = status(305)
    val SwitchProxy = status(306)
    val TemporaryRedirect = status(307)
    val PermanentRedirect = status(308)
    val ResumeIncomplete = status(309)

    val BadRequest = status(400)
    val Unauthorized = status(401)
    val PaymentRequired = status(402)
    val Forbidden = status(403)
    val NotFound = status(404)
    val MethodNotAllowed = status(405)
    val NotAcceptable = status(406)
    val RequestTimeout = status(408)
    val Conflict = status(409)
    val Gone = status(410)
    val LengthRequired = status(411)
    val PreconditionFailed = status(412)
    val PayloadTooLarge = status(413)
    val RequestUriTooLong = status(414)
    val UnsupportedMediaType = status(415)
  }

}