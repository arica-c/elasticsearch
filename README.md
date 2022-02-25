ELASTIC SEARCH PROJECT


CLASSES:

ElasticsearchApplication.java : Entry point of the Spring Application.

services/MysqlConnection.java : A JDBC program that connects to the database and extracts the data.

models/Category.java : A POJO class to structure the data.

controllers/ElasticSearch.java : A java program that creates index, stores Documents and exposes REST endpoint to search through the data stored in ES.


REST ENDPOINTS :

Analyze API : http://localhost:8080/analyze/stringToAnalyze

TERM query : http://localhost:8080/term/fieldToSearch/searchTerm

MATCH query : http://localhost:8080/match/fieldToSearch/searchTerm

PREFIX query: http://localhost:8080/prefix/fieldToSearch/prefix
