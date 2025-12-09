@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.2.0
@REM ----------------------------------------------------------------------------

@echo off
setlocal EnableDelayedExpansion

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"

@REM Download maven-wrapper.jar if not present
if not exist "%WRAPPER_JAR%" (
    echo Downloading maven-wrapper.jar...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '%WRAPPER_JAR%'"
    if errorlevel 1 (
        echo Failed to download maven-wrapper.jar
        exit /b 1
    )
)

@REM Find java.exe
set "JAVA_EXE=java"
if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java"
)

@REM Run Maven
"%JAVA_EXE%" ^
  "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" ^
  -jar "%WRAPPER_JAR%" %*

endlocal
