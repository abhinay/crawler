package org.fingertap.crawler

import java.net.URL

trait HttpClient {

  type Html = String

  def get(url: URL): Html

}
