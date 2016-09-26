package org.fingertap.crawler

import java.net.URL

import org.fingertap.crawler.HtmlParser.StaticContentTags
import org.jsoup.Jsoup

import scala.collection.JavaConversions._
import scala.util.Try

class HtmlParser(html: String, domain: String) {
  val doc = Jsoup.parse(html)

  val links = {
    for (element <- doc.select("a[href]");
         url <- fixedUrl(element.attr("href")); if !ignoreUrl(element.attr("href")))
      yield url
  }.distinct

  val staticContentLinks = {
    StaticContentTags.keys.flatMap(tag => {
      val attr = StaticContentTags(tag)
      val list = doc.select(s"$tag[$attr]")
      for (element <- list;
           url <- fixedUrl(element.attr(attr)); if !ignoreUrl(element.attr(attr)))
        yield url
    }.distinct)
  }

  private def fixedUrl(url: String): Option[URL] =
    Try({
      val newUrl = {
        if (isUrlRelative(url)) s"$domain$url".split("#").head
        else url.split("#").head
      }
      if (newUrl.startsWith("http")) Some(new URL(newUrl))
      else Some(new URL(s"http://$newUrl"))
    }).getOrElse(None)

  private def ignoreUrl(url: String): Boolean =
    url.startsWith("#") ||
    url.isEmpty

  private def isUrlRelative(url: String): Boolean =
    url.startsWith("/")
}

object HtmlParser {
  val StaticContentTags = Map("img" -> "src", "script" -> "src", "link" -> "href")
}
