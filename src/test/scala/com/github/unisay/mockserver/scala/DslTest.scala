package com.github.unisay.mockserver.scala

import com.github.unisay.mockserver.scala.DSL._

class DslTest extends org.scalatest.FunSuite {

  test("dsl") {

    when get "/health" has {
      param("p1", 1) and header("h1", "v1") and header("h2", "v2")
    } respondWith {
      header("h3", "v3") and body("alive")
    }

  }

}
