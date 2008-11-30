@echo off

set CLASSPATH=jsendnsca-cli.jar
set CLASSPATH=%CLASSPATH%;../lib/jsendnsca-core.jar
set CLASSPATH=%CLASSPATH%;../lib/commons-cli-1.1.jar
set CLASSPATH=%CLASSPATH%;../lib/commons-lang-2.4.jar
set CLASSPATH=%CLASSPATH%;../lib/commons-io-1.4.jar

java -classpath %CLASSPATH% com.googlecode.jsendnsca.Main %*
