# scala-akka-otel-java

## setup

### Run otel-collector and jaeger
$ docker compose -f compose.yml up -d

### Down otel-collector and jaeger
$ docker compose -f compose.yml down

### Run Scala App
$ sbt run

### jaeger GUI
http://localhost:16686
