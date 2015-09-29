package crawler.webcrawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

import crawler.control.*;

public class StartCrawl {
	static Logger log = Logger.getLogger(StartCrawl.class);
	public static void main(String args[]) throws IOException{
		PropertyConfigurator.configure("log4j.properties");
		CrawlControl spider = new CrawlControl();
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		spider.search(prop.getProperty("Webpage"),prop.getProperty("Word"));
	}

}
