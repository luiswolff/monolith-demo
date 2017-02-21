if [ -r /vagrant/target/monolith-demo.war ]; then
    echo "Found deployable war file. Copy it to \$JBOSS_HOME/standalone/deployments/"
    cp /vagrant/target/monolith-demo.war /opt/wildfly/standalone/deployments/
else 
    echo "ATTENTION: Could not found artefact for deployment." 1>&2
fi