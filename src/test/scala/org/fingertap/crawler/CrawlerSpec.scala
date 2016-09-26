package org.fingertap.crawler

import java.net.URL

import org.scalatest.{FlatSpec, Matchers}

class CrawlerSpec extends FlatSpec with Matchers {

  "Crawler" should "produce empty sitemap for page with no HTML" in {
    val crawler = new Crawler(new URL("http://www.test.com/empty.html"), new TestHttpClient)
    crawler.siteMap should be(List.empty[URL])
  }

  "Crawler" should "produce a simple sitemap with a single link" in {
    val crawler = new Crawler(new URL("http://www.test.com/single.html"), new TestHttpClient)
    crawler.siteMap.size should equal(1)
    crawler.siteMap should contain(new URL("http://www.test.com/page1.html"))
  }

  "Crawler" should "produce a sitemap with multiple links" in {
    val crawler = new Crawler(new URL("http://www.test.com/multiple.html"), new TestHttpClient)
    crawler.siteMap.size should equal(3)
    crawler.siteMap should contain(new URL("http://www.test.com/page1.html"))
    crawler.siteMap should contain(new URL("http://www.test.com/page2.html"))
    crawler.siteMap should contain(new URL("http://www.test.com/page3.html"))
  }

  "Crawler" should "include external links in sitemap without trying to scrape them" in {
    val crawler = new Crawler(new URL("http://www.test.com/multiple-with-external.html"), new TestHttpClient)
    crawler.siteMap should contain(new URL("http://www.google.com"))
  }

  "Crawler" should "include links to external content in sitemap" in {
    val crawler = new Crawler(new URL("http://www.test.com/static-content.html"), new TestHttpClient)
    crawler.siteMap should contain(new URL("http://www.test.com/images/foo.png"))
    crawler.siteMap should contain(new URL("http://www.test.com/css/styles.css"))
  }

}
