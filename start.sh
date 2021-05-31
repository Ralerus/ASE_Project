mvn clean package;
cd target/classes;
cp ~/.m2/repository/org/xerial/sqlite-jdbc/3.34.0/sqlite-jdbc-3.34.0.jar .
cp ../../src/main/resources/icon.png layer/presentation
java -classpath ".:sqlite-jdbc-3.34.0.jar" application.Application