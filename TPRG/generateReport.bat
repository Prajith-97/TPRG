@ECHO ON
CD %~dp0
allure serve %*
ECHO Successfully Finished generating allure reports!
cmd /k
EXIT