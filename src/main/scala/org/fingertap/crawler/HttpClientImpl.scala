package org.fingertap.crawler
import java.net.URL

import scala.util.Try
import scalaj.http.Http

class HttpClientImpl extends HttpClient {
  override def get(url: URL): Html = {
    Try({
      val response = Http(url.toString).asString
      response.code match {
        case 200 => response.body
        case _ => ""
      }
    }).getOrElse("")
  }
}
