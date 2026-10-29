@echo off
setlocal
for %%I in ("%~dp0.") do set "BASEDIR=%%~sI"
set "WRAPPER=%BASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set "MAVEN_PROJECTBASEDIR=%BASEDIR%"
if not exist "%WRAPPER%" (
  echo Downloading the Maven Wrapper bootstrap. Internet access is needed once.
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar' -OutFile '%WRAPPER%'"
  if errorlevel 1 exit /b 1
)
java -classpath "%WRAPPER%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
exit /b %errorlevel%
