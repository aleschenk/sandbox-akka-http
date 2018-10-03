package com.book

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Tcp.ServerBinding
import akka.stream.scaladsl.{Sink, Tcp}

import scala.concurrent.Future

object EchoServer extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val binding: Future[ServerBinding] =
    Tcp().bind("127.0.0.1", 888)
      .to(Sink.ignore).run()


  binding.map {b =>
    b.unbind() onComplete {
      case _ =>
    }
  }
}
