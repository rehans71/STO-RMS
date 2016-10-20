@echo off
rem -------------------------------------------------------------------------
rem Build configuration parameter definitions
rem -------------------------------------------------------------------------
rem

set skipTests=-Dmaven.test.skip=true

call mvn %skipTests% clean package
