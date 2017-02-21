echo "Install openjdk 8"

apt-get update
apt-get install -y openjdk-8-jdk

echo "install unzip"
apt-get install unzip

echo "Download wildfly server"
wget http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.zip
unzip wildfly-10.1.0.Final.zip -d /opt/

echo "Create symbolic link"

ln -s /opt/wildfly-10.1.0.Final /opt/wildfly

echo "Write default config file"

echo JAVA_HOME=\"/usr/lib/jvm/java-8-openjdk-amd64/\"   >> /etc/default/wildfly
echo JBOSS_HOME=\"/opt/wildfly\" >> /etc/default/wildfly
echo JBOSS_USER=wildfly >> /etc/default/wildfly
echo JBOSS_MODE=standalone >> /etc/default/wildfly
echo JBOSS_CONFIG=standalone.xml >> /etc/default/wildfly
echo STARTUP_WAIT=60 >> /etc/default/wildfly
echo SHUTDOWN_WAIT=60 >> /etc/default/wildfly
echo JBOSS_CONSOLE_LOG=\"/var/log/wildfly/console.log\" >> /etc/default/wildfly
echo JBOSS_OPTS=\"-b 0.0.0.0 -bmanagement 0.0.0.0\" >> /etc/default/wildfly
echo export JAVA_OPTS=\"-Xms2400m -Xmx2400m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true\" >> /etc/default/wildfly
echo export JAVA_OPTS=\"\$JAVA_OPTS -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true\" >> /etc/default/wildfly

echo "Copy init script"
cp /opt/wildfly/docs/contrib/scripts/init.d/wildfly-init-debian.sh /etc/init.d/wildfly

echo "Add Wildfly as a service"
update-rc.d wildfly defaults
update-rc.d wildfly enable

echo "Create directories"

mkdir -p /var/log/wildfly
mkdir -p /home/wildfly

echo "create os user"
useradd --system --shell /bin/false wildfly

echo "change owner of Wildfly directoriesw"

chown -R wildfly:wildfly /opt/wildfly-10.1.0.Final
chown -R wildfly:wildfly /opt/wildfly
chown -R wildfly:wildfly /var/log/wildfly
chown -R wildfly:wildfly /home/wildfly

echo "Start service"
service wildfly start

echo "create management user"
/opt/wildfly/bin/add-user.sh -u admin -p admin -e -cw