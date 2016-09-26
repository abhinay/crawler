package org.fingertap.crawler

import java.io.{File, PrintWriter}
import java.net.URL

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object CrawlerApp extends App {
  if (args.isEmpty) {
    println("Please provide a starting URL")
    System.exit(1)
  }

  private val startUrl: String = args(0)
  private val crawler: Crawler = new Crawler(new URL(startUrl), new HttpClientImpl)

  Future {
    Thread.sleep(2000)
    val spinnerQueue = new mutable.Queue[Char]()
    spinnerQueue.enqueue('|','/','-','\\','\\')

    while (crawler.todoList.nonEmpty) {
      val nextSpinChar = spinnerQueue.dequeue()
      print(s"\r Crawling $nextSpinChar ")
      spinnerQueue.enqueue(nextSpinChar)
      Thread.sleep(500)
    }
  }

  val writer = new PrintWriter(new File("sitemap.txt" ))
  writer.write(crawler.siteMap.mkString("\n"))
  writer.close()

  println("Done!")
  println("Results saved to sitemap.txt")
}
