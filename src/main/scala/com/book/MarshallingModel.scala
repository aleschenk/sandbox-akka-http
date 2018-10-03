package com.book

import akka.http.scaladsl.marshalling.{Marshal, Marshalling}
import akka.http.scaladsl.model.{ContentTypes, MessageEntity}
import scala.concurrent.duration._
import scala.concurrent.Await

object MarshallingModel extends App {

  val aa = Marshalling.WithFixedContentType(
    contentType = ContentTypes.`text/plain(UTF-8)`,
    marshal = () => "")

  aa.map[String]((value: String) => s"Hola $value")

  val system = akka.actor.ActorSystem("system") // this was missing!
  import system.dispatcher

  val string = "Yeah"
  val entityFuture = Marshal(string).to[MessageEntity]
  val entity = Await.result(entityFuture, 1.second)

}
