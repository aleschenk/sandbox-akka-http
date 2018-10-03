package com.book

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{HttpApp, Route}

object SimpleWebServer extends HttpApp {
  override protected def routes: Route =
    path("vehicles" / "catalog") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
          "<h1>Sah hello</h1>"))
      }
    } ~
      path("vehicles" / "catalog" / IntNumber) { carId =>
        complete(s"carId: $carId")
      }

//  val route: Route = { ctx => ctx.complete("yeah!") }
//
//  val route2: Route = _.complete("yeah")
//
//  val route3 = complete("yeah")
//
//  val a: Route = {
//    println("MARK")
//    ctx => ctx.complete("yeah")
//  }
//
//  val b: Route = { ctx =>
//    println("MARK")
//    ctx.complete("yeah")
//  }

  SimpleWebServer.startServer("localhost", 8080)
}
