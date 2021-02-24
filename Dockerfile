FROM maven:3.6.3-openjdk-8-slim as build
WORKDIR /opt/build
COPY . /opt/build
RUN mvn clean install --no-transfer-progress

FROM jboss/wildfly
COPY --from=build /opt/build/target/advStand.war /opt/jboss/wildfly/standalone/deployments/
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]