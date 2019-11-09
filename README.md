# Shift Tracer

## Core
Simple Java class to switch between different ```io.opentracing.Tracer``` implementation.

It's meant to work in Kubernetes environment (also OpenShift), to switch between different tracers implementation at deployment time.

This can be achieved by setting the parameter ```TRACER_NAME``` to the preferred implementation:
* Jaeger
* Datadog

## App
Simple Spring Boot Application exposing two REST endpoints for the purpose of generating traces:
* http://localhost:8080/giveMeFive
* http://localhost:8080/reapet/this

### Build
The Shift Tracer can be built using Maven or Docker. 

#### Maven
Building with Maven is only needed to push to your private repository and have the other application define a dependencies to the ```com.datadog.opentracing:core``` module.

To build the module, do sa follows:

```bash
mvn -e clean install
```

#### Docker
Building with Maven is only needed to push to your private repository and have the other application define a dependencies to the ```com.datadog.opentracing:core``` module.

To build the module, do sa follows:

```bash
docker build -t shift-tracer:latest .
```

### Testing
To test the Shift Tracer, use the ```docker-compose``` command, as follows:

```docker-compose up```

This will rely on the ```docker-compose.yaml``` file provided with the code.
It will run the following containers:
* Jaeger Agent
* Jaeger Collector
* Jaeger Query
* Cassandra
* Datadog Agent
* Shift Application
 
Once all containers are running, the traces can be generate using the ```curl``` command, as follows:
```bash
curl -vk http://localhost:8080/giveMeFive
curl -vk http://localhost:8080/reapet/this
```

#### Jaeger UI

![Jaeger UI](jaeger-ui-01.png)

#### Datadog APM Traces

![Datadog APM Traces](datadog-apm-traces-01.png)

![Datadog APM Traces](datadog-apm-traces-02.png)


## Pros
Developers just need to add the OpenTracing and ShiftTracer dependencies to their code, and decide at deployment time to switch between tracer implementation.

A common use case would be:
* Jaeger for dev and test
* Datadog for pre-prod and prod

No build required for the application, just redeploy.
