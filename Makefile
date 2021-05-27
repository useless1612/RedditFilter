all:
		mvn compile
		mvn exec:java

clean:
		mvn clean