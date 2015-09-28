package crawler.webcrawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;

import crawler.control.*;

public class SpiderTest {
	public static void main(String args[]) throws IOException{
		BasicConfigurator.configure();
		Spider spider = new Spider();
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		spider.search(prop.getProperty("Webpage"),prop.getProperty("Word"));
	}

}
