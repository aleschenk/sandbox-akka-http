package com.book

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

object MiniServer2 extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end
  implicit val executionContext = system.dispatcher

  val route =
    get {
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<html><body>Hello world!</body></html>"))
      } ~
        path("") {
          complete("")
        } ~
        path("vehicle" / "catalog" / IntNumber) { carId =>
          complete(s"CarId: $carId")
        } ~
        path("crash") {
          sys.error("BOOM!")
        }
    }

  val route2 =
    pathSingleSlash {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<html><body>Hello world!</body></html>"))
    } ~
      path("vehicle" / "catalog") {
        get {
          complete(s"Catalog")
        }
      } ~
      path("vehicle" / "catalog" / "index") {
        put {
          complete(s"Indexing please wait.")
        }
      } ~
      path("vehicle" / "catalog" / IntNumber) { carId =>
        complete(s"CarId: $carId")
      } ~
      path("crash") {
        sys.error("BOOM!")
      }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

//PUT "/vehicle/catalog/index"
//GET "/vehicle/catalog{carId}"
//GET "/vehicle/catalog/brands"
//GET "/vehicle/catalog/models"
//GET "/vehicle/catalog" "q" "brand" "model" "year" "engine" "doors"
