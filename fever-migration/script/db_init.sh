#!/bin/zsh
echo "[INFO] database start migration."
cd ../
mvn flyway:migrate -P develop