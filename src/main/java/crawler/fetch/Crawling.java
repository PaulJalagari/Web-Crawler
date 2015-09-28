package crawler.fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.control.Spider;
import crawler.webcrawler.NextCrawl;


public class Crawling {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;
	static Logger log=Logger.getLogger(Spider.class);
	public List<String> crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			if (connection.response().statusCode() == 200) // 200 is the HTTP OK
															// status code
															// indicating that
															// everything is
															// great.
			{
				log.info("\n**Visiting** Received web page at " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				log.fatal("**Failure** Retrieved something other than HTML");
				return null;
			}
			Elements linksOnPage = htmlDocument.select("a[href*=2014]");
			log.info("Found (" + linksOnPage.size() + ") links");
			List<String> LinksList = new ArrayList<String>();
			for (Element link : linksOnPage) {
				log.info("link : " + link.attr("abs:href"));
				LinksList.add(link.attr("abs:href"));
				this.links.add(link.absUrl("href"));
				NextCrawl crawl=new NextCrawl();
				crawl.crawlAgain(link.attr("abs:href"));
							}
			return LinksList;
		} catch (IOException ioe) {
			// We were not successful in our HTTP request
			return null;
		}
	}

	/**
	 * Performs a search on the body of on the HTML document that is retrieved.
	 * This method should only be called after a successful crawl.
	 * 
	 * @param searchWord
	 *            - The word or string to look for
	 * @return whether or not the word was found
	 */
	public boolean searchForWord(String searchWord) {
		// Defensive coding. This method should only be used after a successful
		// crawl.
		if (this.htmlDocument == null) {
			log.fatal("ERROR! Call crawl() before performing analysis on the document");
			return false;
		}
		log.info("Searching for the word " + searchWord + "...");
		String bodyText = this.htmlDocument.body().text();
		return bodyText.toLowerCase().contains(searchWord.toLowerCase());
	}

	public List<String> getLinks() {
		return this.links;
	}


}
