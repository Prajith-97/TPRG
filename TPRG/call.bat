@ECHO ON
setlocal enabledelayedexpansion
SET str=%1
ECHO %~dp0
ECHO %1
SET "modified=!str:#= or !"
CD %~dp0
ECHO %modified%
mvn clean test -Dcucumber.filter.tags="%modified%"
REM mvn clean install allure:report
ECHO Successfully Finished!
cmd /k
EXIT