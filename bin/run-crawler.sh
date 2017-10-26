mvn clean install
( cd crawler && mvn package -Dstorm.scope=compile -Pbigjar && java -cp target/crawler-standalone.jar lt.tokenmill.crawlingframeworkdemo.crawler.DemoCrawlerTopology -local -conf conf/crawler-local.yaml )
