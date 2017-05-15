@echo off
cd ../../../
call mvn flyway:migrate
pause