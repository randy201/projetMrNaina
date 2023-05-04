javac -d . *.java
jar -cf framework.jar ./etu1989
xcopy framework.jar "../test-framework/WEB-INF/lib"
cd "../test-framework/WEB-INF/classes"
javac -cp "../lib/framework.jar" -d . *.java
cd "../../"
jar -cf affiche.war .
xcopy affiche.war "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps"