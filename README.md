# Website Crawler

## Overview

A simple web crawler written in Scala.

The crawler is limited to one domain. So given a starting URL it visits all pages within the domain, but does not follow the links to external sites such as Google or Twitter.

The output is a simple site map saved in a file called sitemap.txt in the location from which you ran the tool.

The site map file contains links to other pages under the same domain, links to static content such as images, and to external URLs.

## Dev Setup

### Prerequisites

To be able to run the project and its tests on your machine, you need to have the following:  

* Java 8
* Scala version 2.11 or greater
* SBT version 0.13.8 or greater

### Tests

To run tests:

> sbt test

> sbt it:test

### Build

To build the JAR:

> sbt assembly

## Execute

To run the crawler using the pre-built JAR in bin:

> java -jar bin/crawler-assembly-1.0.jar [URL]

Example:

> java -jar bin/crawler-assembly-1.0.jar http://wiprodigital.com

A file called sitemap.txt will be saved in the location from which you ran the command.

## Limitations

I was asked to spend two hours on this exercise, therefore there are compromises that had to be made, which can obviously be addressed given more time:

* The crawler crawls links sequentially, not parallel
* Tests could be more thorough in testing edge cases
* No command line options to change name of file or tweak settings
* No progress on crawl status
* Needs better error reporting to end user
