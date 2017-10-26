package lt.tokenmill.crawlingframeworkdemo.crawler;

import com.digitalpebble.stormcrawler.ConfigurableTopology;
import lt.tokenmill.crawling.crawler.CrawlerTopology;

public class DemoCrawlerTopology extends CrawlerTopology {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        ConfigurableTopology.start(new DemoCrawlerTopology(), args);
    }

}
