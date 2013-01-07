#!/bin/sh
export JAVA_HOME=/opt/java
export JAVA_OPTS="$JAVA_OPTS -XX:MaxPermSize=256m -Xms256m -Xmx1024m -DzoneFile=file:///opt/wso2greg/4.5.2/bin/test.integration.properties"
