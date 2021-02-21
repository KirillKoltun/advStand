#Dockerfile

FROM jboss/wildfly

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

ADD target/advStand.war /opt/jboss/wildfly/standalone/deployments/