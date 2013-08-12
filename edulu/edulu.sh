#!/bin/bash

### BEGIN INIT INFO
# Provides: edulu
# Required-Start: $remote_fs $syslog
# Required-Stop: $remote_fs $syslog
# Default-Start: 3 4 5
# Default-Stop: 0 1 6
# Short-Description: Start Edulu Server at boot time
# Description: Start Edulu Server at boot time
### END INIT INFO



function start() {
        	echo "Starting edulu ..."
            JAVA_HOME=/opt/java
            export JAVA_HOME
            sh /opt/tomcat/bin/startup.sh
            # wget http://localhost/wikistudy 2>/dev/null 1>&2
}

function stop() {
            JAVA_HOME=/opt/java
            export JAVA_HOME
        	echo "Stopping edulu ..."
			sh /opt/tomcat/bin/shutdown.sh
}

case "$1" in
	start)
		start
	;;
	stop)
		stop
	;;
	restart)
		stop
		start
	;;

	*)
		echo "Usage: $0 (start|stop|restart)"
	;;
esac
