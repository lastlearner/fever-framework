@echo off
cd ..
call mvn flyway:migrate -P develop
pause