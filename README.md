# Alltomator
Personal plugin for Eclipse.

The code basically execute tasks named with a prefix.
For now, the order is Maven Builders followed by Apache Tomcat server configuration.

For example:
Create a Maven Builder run configuration with AT_1_mavenTask name and a Apache Tomcat run configuration named AT_2_tomcatTask.

Execute Alltomator and will get:
1. Workspace full refresh.
2. AT_1_mavenTask executed.
3. Workspace full refresh. (Becouse Apache Tomcat neede this.)
4. Workspace full build.
5. Full clean/publish for all configured servers.
6. AT_2_tomcatTask executed. (Server started in debug mode.)


A good time is saved when we need execute a lot of Maven tasks before start the server.
When this tasks can't be aggregated.

And... I was learn a lot.

# Installation
Use the UpdateServer URL.
https://raw.githubusercontent.com/hidertanure/alltomator/master/AlltomatorUpdateSite/site.xml
