FROM tomcat:8.5.50-jdk8-openjdk

COPY ./tasks-backend.war /usr/local/tomcat/webapps/tasks.war