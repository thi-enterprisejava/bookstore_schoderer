FROM jboss/wildfly

# Set some environment variables
ENV MARIADB_VERSION 1.3.3

# Add the jboss-cli commands for postgres
ADD commands.cli /tmp/commands.cli

# Download postgres, configure jboss-cli for parameter resolving, run jboss-cli, and clean-up
RUN cd $HOME \
    && curl -O http://central.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/$MARIADB_VERSION/mariadb-java-client-$MARIADB_VERSION.jar \
    && mv $HOME/mariadb-java-client-$MARIADB_VERSION.jar /tmp/ \
    && echo "mariadb.version=${MARIADB_VERSION}" > /tmp/env.properties \
    && $JBOSS_HOME/bin/jboss-cli.sh --properties=/tmp/env.properties --file=/tmp/commands.cli \
    && rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history \
    && rm /tmp/mariadb-java-client-$MARIADB_VERSION.jar /tmp/env.properties

# Expose the ports we're interested in
EXPOSE 8080 9990

# Set the default entrypoint to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]