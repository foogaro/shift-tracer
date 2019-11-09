FROM maven:3.6.0-jdk-8 AS build

USER root
RUN mkdir -p /srv
COPY . /srv
WORKDIR /srv
RUN mvn -e clean install

FROM rawmind/alpine-jdk8:1.8.181-0

USER root
WORKDIR /srv
COPY --from=build /srv/app/target/app-0.1.0-SNAPSHOT.jar app.jar

# >>>>>>>>>> DATADOG
#RUN wget -O dd-java-agent.jar 'https://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.datadoghq&a=dd-java-agent&v=LATEST'
ENV DD_JMXFETCH_ENABLED=true
ENV DD_INTEGRATIONS_ENABLED=true
ENV DD_INTEGRATION_SERVLET_ENABLED=true
ENV DD_INTEGRATION_JAXRS_ENABLED=true
#ENV DD_AGENT_HOST=status.hostIP
ENV DD_AGENT_HOST=datadog-agent
ENV DD_TRACE_AGENT_PORT=8126
#ENV TRACER_NAME=DATADOG
# <<<<<<<<<< DATADOG

# >>>>>>>>>> JAEGER
ENV JAEGER_AGENT_HOST=jaeger-collector
ENV JAEGER_AGENT_PORT=14268
ENV JAEGER_SAMPLER_MANAGER_HOST_PORT=jaeger-agent:5778
ENV JAEGER_AGENT_HOST=jaeger-agent
ENV JAEGER_AGENT_PORT=6831
#ENV TRACER_NAME=JAEGER
# <<<<<<<<<< JAEGER

EXPOSE 8080/tcp

ENTRYPOINT ["java"]

CMD ["-jar", "/srv/app.jar"]

