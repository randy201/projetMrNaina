@REM javac -d "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps\Mr Naina\WEB-INF\classes" *.java
@REM javac -d "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps\framerwok" *.java
javac -d . *.java
jar -cf framework.jar "./etu1989/"
xcopy framework.jar "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\webapps\\test-framework\\WEB-INF\\lib"
cd /d "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\webapps\\test-framework"
jar -cf test-fram.war .
xcopy test-fram.war ../

@REM jar -cf test-fram.war "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.0\\webapps\\test-framework\\WEB-INF\\lib" .

