package com.github.unisay.mockserver.scala

import org.mockserver.model.HttpRequest.{request => mockRequest}
import org.mockserver.model.HttpResponse.{response => mockResponse}
import org.mockserver.model.{HttpRequest => MockRequest, HttpResponse => MockResponse}

object DSL {
  
  trait Composable {
    def and(composable: Composable): Composable = ???
  }
  
  trait ExpectationBuilder {
    def has(condition: Composable): ExpectationBuilder = ???
    def respondWith(composable: Composable): Unit = ???
  }

  case class param(name: String, value: Any) extends Composable
  
  case class header(name: String, value: Any) extends Composable
  
  case class body(content: String) extends Composable

  object when {
    def get(path: String): ExpectationBuilder = ???
  }

}
