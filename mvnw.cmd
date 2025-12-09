@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.2.0
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE__=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0telefonzentrale'; $env:__MVNW_SCRIPT_DIR__=$scriptDir; Get-Content '%~dp0.mvn\wrapper\maven-wrapper.properties' -ErrorAction Stop | ForEach-Object { if ($_.StartsWith('distributionUrl=')) { 'MVNW_DIST_URL=' + $_.Substring(16) } }}"`) DO @SET __MVNW_CMD__=%%A&& SET __MVNW_ERROR__=%%B
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE__%
@IF NOT "%__MVNW_ERROR__%"=="" @GOTO :error

@IF "%__MVNW_CMD__%"=="" @GOTO :usage

@SETLOCAL
@SET JAVA_EXE=java.exe
@IF NOT "%JAVA_HOME%"=="" @SET JAVA_EXE=%JAVA_HOME%\bin\java.exe

@SET WRAPPER_JAR="%~dp0.mvn\wrapper\maven-wrapper.jar"
@IF NOT EXIST %WRAPPER_JAR% (
  @ECHO Downloading maven-wrapper.jar...
  @powershell -noprofile -command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '%~dp0.mvn\wrapper\maven-wrapper.jar' }"
)

@SET MAVEN_PROJECTBASEDIR=%~dp0
@%JAVA_EXE% -Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR% -jar %WRAPPER_JAR% %*
@ENDLOCAL
@GOTO :eof

:error
@ECHO Error: %__MVNW_ERROR__%
@GOTO :eof

:usage
@ECHO Usage: %__MVNW_ARG0_NAME__% [options] [goal(s)]
@ECHO.
@ECHO This is the Apache Maven Wrapper.
@ECHO Run this script to automatically download and run Maven.
