mvn clean install
( cd crawler-management-ui && mvn package && java -Dconfig=conf/development.properties -jar target/crawler-management-ui-0.1.0-standalone.jar )