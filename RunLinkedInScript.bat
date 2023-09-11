@echo off
set "batchPath=%~dp0"
echo The location of the currently running batch file is: %batchPath%
echo running testng.xml
cd /d "%batchPath%"
mvn test -DsuiteXmlFiles=testng.xml
