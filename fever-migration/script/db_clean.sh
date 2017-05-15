#!/bin/zsh
echo "[INFO] database clean."
mysql -u root -h localhost -p <<EOF
USE database;
SET SESSION FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS schema_version;
EOF