package com.book

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

object JsonMarshall extends App {

  final case class Item(name: String, id: Long)
  final case class Order(items: List[Item])

  trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val itemFormat = jsonFormat2(Item)
    implicit val orderFormat = jsonFormat1(Order)
  }


}
