@echo off
rem -------------------------------------------------------------------------
rem Configuration parameter definitions
rem -------------------------------------------------------------------------
rem

set configFile=config/production.properties

rem -------------------------------------------------------------------------
rem Invoke build-app.bat to build jar file
rem -------------------------------------------------------------------------
rem

call build-app.bat

rem -------------------------------------------------------------------------
rem run SpringBoot app
rem -------------------------------------------------------------------------
rem

java -jar target/sto-rms.jar --spring.config.location=%configFile%