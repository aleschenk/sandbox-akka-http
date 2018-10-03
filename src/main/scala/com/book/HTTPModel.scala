package com.book

import akka.http.scaladsl.model.HttpMethods.{GET, POST}
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials, Location}
import akka.stream.scaladsl.Source
import akka.util.ByteString

object HTTPModel extends App {

  // construct a simple GET request to `homeUri`
  val homeUri = Uri("/")

  // construct simple GET request to "/index" (implicit string to Uri conversion)
  HttpRequest(GET, uri = "/index")

  // construct simple POST request containing entity
  val data = ByteString("abc")
  HttpRequest(POST, uri = "/receive", entity = data)

  // customize every detail of HTTP request
  val userData = ByteString("abc")
  //  val authorization = headers.Authorization(BasicHttpCredentials("user", "pass"))
  //  HttpRequest(
  //    PUT,
  //    uri = "/user",
  //    entity = HttpEntity(`text/plain` withCharset `UTF-8`, userData),
  //    headers = List(authorization),
  //    protocol = `HTTP/1.0`)


  HttpResponse(status = StatusCodes.OK, entity = HttpEntity.Empty)

  HttpEntity.Strict(contentType = ContentTypes.`text/plain(UTF-8)`, data = ByteString(""))

  // val array: Array[String] = Array[String]("a")
  HttpEntity.Default(contentType = ContentTypes.`text/plain(UTF-8)`, contentLength = 12, data = Source.single(ByteString("ABC")))

  //  The model for HTTP/1.1 chunked content (i.e. sent with Transfer-Encoding: chunked). The content length is unknown and the individual chunks are presented as a Source[HttpEntity.ChunkStreamPart]
  //  val source = Source[HttpEntity.ChunkStreamPart]
  //  HttpEntity.Chunked(contentType = ContentTypes.`text/plain(UTF-8)`, chunks = source)

  // An unchunked entity of unknown length that is implicitly delimited by closing the connection (Connection: close)
  //  HttpEntity.CloseDelimited(contentType = ContentTypes.`text/plain(UTF-8)`, data  = )

  val helloWorldHttpEntity = HttpEntity("Hello World")

  val loc = Location("http://example.com/other")

  val auth = Authorization(BasicHttpCredentials("joe", "josepp"))

  case class User(name: String, pass: String)

  // a method that extracts basic HTTP credentials from a request
  def credentialsOfRequest(req: HttpRequest): Option[User] =
    for {
      Authorization(BasicHttpCredentials(user, pass)) <- req.header[Authorization]
    } yield User(user, pass)

}
