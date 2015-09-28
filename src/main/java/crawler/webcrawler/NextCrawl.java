package crawler.webcrawler;

import java.util.ArrayList;
import java.util.List;

import crawler.control.Spider;
import crawler.download.*;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NextCrawl {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	static Logger log=Logger.getLogger(Spider.class);

	public List<String> crawlAgain(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
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
			}
			Elements linksOnPage = htmlDocument.select("a[href*=2014]");
			log.info("Found (" + linksOnPage.size() + ") links");
			List<String> InnerLinks = new ArrayList<String>();
			for (Element link : linksOnPage) {
				HttpDownloader download=new HttpDownloader();
				download.download(link.attr("abs:href"));
				log.info("Inner links: " + link.attr("abs:href"));
				InnerLinks.add(link.attr("abs:href"));
			}
			return InnerLinks;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}