package org.fingertap.crawler

import java.net.URL

import scala.collection.mutable

class Crawler(startUrl: URL, client: HttpClient) {
  val domainPr = s"${startUrl.getProtocol}://${startUrl.getHost}"
  val todoList = new mutable.Queue[URL]()
  val doneList = new mutable.HashSet[URL]()

  lazy val siteMap = {
    todoList.enqueue(startUrl)
    buildSiteMap()
  }

  private def buildSiteMap(): Seq[URL] = {
    val siteMap = new mutable.LinkedHashSet[URL]()

    while(todoList.nonEmpty) {
      val url  = todoList.dequeue()
      val html = client.get(url)
      val doc  = new HtmlParser(html, domainPr)

      siteMap ++= doc.links ++= doc.staticContentLinks

      doneList.add(url)
      todoList.enqueue(linksToCrawl(doc.links):_*)
    }

    siteMap.toList
  }

  private def linksToCrawl(links: Seq[URL]) =
    links
      .filter(isSameDomainUrl)
      .filterNot(todoList.contains)
      .filterNot(doneList.contains)

  private def isSameDomainUrl(url: URL): Boolean =
    startUrl.getHost == url.getHost

}
