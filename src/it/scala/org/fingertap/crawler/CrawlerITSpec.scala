package org.fingertap.crawler

import java.net.URL

import org.scalatest.{FlatSpec, Matchers}

class CrawlerITSpec extends FlatSpec with Matchers {

  "Crawler" should "produce sitemap from external website" in {
    val crawler = new Crawler(new URL("http://wiprodigital.com"), new HttpClientImpl)
    crawler.siteMap.size should be > 0
  }

}
