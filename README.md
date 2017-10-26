# Crawling Framework Example

This repository contains a sample [Crawling Framework](https://github.com/tokenmill/crawling-framework) configuration. It shows how to use Crawling Framework to setup a simple science news crawler, write results to [ElasticSearch](https://www.elastic.co/products/elasticsearch). You can use this configuration to set up your own crawler.

There will be no need to write any code, just change the configuration files, create ElasticSearch indices (scripts are provided), and use Crawling Framework Management UI to define crawling rules.

## Running Science News Crawler

### Prerequisites

ElasticSearch v5.5.x server must be running on localhost:9200 before executing installation commands.

### Installing and running management UI

First you need to checkout Crawling Framework which has scripts to create ElasticSearch indices. Make sure ElasticSearch server is running (default configuration assumes that it's host is localhost, if not see instructions bellow how to change it). Run the commands bellow:

```
git clone git@github.com:tokenmill/crawling-framework.git
cd crawling-framework
bin/create-es-indices.sh localhost cf-example
```

After that clone Example project and run crawling management UI.

```
git clone git@github.com:tokenmill/crawling-framework-example.git
cd crawling-framework-example
bin/run-management-ui.sh
```

Once started open http://localhost:8081/ which will show empty crawling setup. In order to populate it with pre-configured Science News setup:

1. Click *Configuration* and choose *Import/Export* option. This will open Management UI section where configurations can be imported or exported (once you create your own configuration you might want to save it via export).#
1. Window opens on *HTTP Sources* tab. Click on *Browse* and choose (paths are relative to example project root) *crawl-config/http-sources.csv* Then click on *Import HTTP Sources*
1. Open *HTTP Source Tests* tab. Click on *Browse* and choose *crawl-config/http-source-tests.csv* Then click on *Import HTTP Source Tests*

Confirm that there are three HTTP sources configured. Click *Configuration* and choose *HTTP Sources* option. Table bellow has to contain entries for:

1. https://www.sciencedaily.com
1. https://www.sciencenews.org
1. http://www.bbc.com/news/science_and_environment

Crawl setup is done and we can start the crawler.

### Running the crawl

From within Example project root run this command
```
bin/run-crawler.sh
```

It will start Storm Crawler with the configuration for Science News crawl. Run it for a few minutes, you should start seeing log messages with urls in it. Something like this
```
7-09-20T10:25:41 source: http://www.bbc.com/news/science_and_environment

24411 [Thread-36-generator-executor[4 4]] INFO  l.t.c.c.s.UrlGeneratorSpout - Emitted url http://www.bbc.com/news/science-environment-40900679 with meta discovered: 2017-09-20T10:25:41 source: http://www.bbc.com/news/science_and_environment

24411 [Thread-36-generator-executor[4 4]] INFO  l.t.c.c.s.UrlGeneratorSpout - Emitted url http://www.bbc.com/news/science-environment-40686984 with meta discovered: 2017-09-20T10:25:41 source: http://www.bbc.com/news/science_and_environment

```

Crawler is working and writes found documents to ElasticSearch. You can leave crawler running for as long as you like. In order see the results issue the following query via curl or using Kibana.

```
curl -XGET "http://localhost:9200/cf-example-docs/_search" -H 'Content-Type: application/json' -d'
{"query": {"match_all": {}}}'
```


## Configuring your own crawler

Use Example project as the basis for your own crawler. Just as in case of Example you have to create new indices via framework's scripts


```
cd [CRAWLING FRAMEWORK LOCATION]
bin/create-es-indices.sh localhost [APP NAME]
```

Next you will have to change Storm configuration file located at *crawler/conf/crawler-local.yaml* 

```
# Index to store http source configuration
es.httpsource.index.name=[APP NAME]-http-sources

# Index to store http source tests
es.httpsourcetest.index.name=[APP NAME]-http-source-tests

# Index to store named queries
es.namedqueries.index.name=[APP NAME]-named-queries

# Index to store crawled documents
es.docs.index.name=[APP NAME]-docs
```

This gets the configuration in order and you can run crawler management UI *bin/run-management-ui.sh* 

Setup your crawl sources and after Storm Crawler can be started to fetch the specified content *bin/run-crawler.sh*
