FROM docker.target.com/toolshed/base-jdk:17-jre-linux-amd64

ADD build/distributions/springreactform.tar /

# Enable Target standard metrics
ENV OTEL_RESOURCE_ATTRIBUTES=service.name=springreactform
ENV JAVA_TOOL_OPTIONS=-javaagent:/app/otel-target-extensions.jar

ENTRYPOINT ["/usr/bin/dumb-init", "--", "/springreactform/bin/springreactform"]