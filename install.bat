@echo off
echo [INFO] 确保默认JDK版本为JDK5.0及以上版本.

cd %~dp0
call mvn clean source:jar install -U -Dmaven.test.skip=true

pause