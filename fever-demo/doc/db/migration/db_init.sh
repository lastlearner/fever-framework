#!/bin/bash

echo "[INFO] database start migration."

cd ../../../

mvn flyway:migrate