package org.fingertap.crawler

import java.net.URL

import scala.io.Source._

class TestHttpClient extends HttpClient {
  override def get(url: URL): Html = {
    val urlToFileName: String = url.toString.replace('/', '-')
    fromInputStream(this.getClass.getResourceAsStream(urlToFileName))
      .getLines()
      .mkString
  }
}
