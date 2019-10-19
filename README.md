# Event Filter

This program aggregates an arbitrary number of reports following a common pre-specified schema into one CSV file.

## Compiling
Project management is handled by `maven`. An executable JAR can be created by issuing the following command from the root folder of the repository: `mvn clean compile assembly:single`
 
The executable JAR can be executed as follows: 
- `java -jar target/eventfilter-1.0-SNAPSHOT-jar-with-dependencies.jar <output> <inputs>`
- where `<output>` is the destination file of the aggregated report.
- and `<inputs>` is a space-separated list of files that you would like to aggregate

i.e. `java -jar target/eventfilter-1.0-SNAPSHOT-jar-with-dependencies.jar output.csv reports.csv reports.json reports.xml`

## Justifications
- I used `maven` because it is an industry standard dependency and project management tool.
- `commons-csv`, `json-simple`, and `dom4j` appear to be commonly used libraries for parsing CSV, JSON, and XML respectively, however it would be straightforward to swap these libraries for something else.
- I pinned the output timezone to `GMT-04:00` because the use of three-letter time zone IDs is [deprecated](https://docs.oracle.com/javase/7/docs/api/java/util/TimeZone.html).