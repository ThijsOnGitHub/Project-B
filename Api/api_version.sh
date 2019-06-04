#!/bin/bash

db=DATABASE

mysql -u local -e "SELECT data_version FROM ${db}.app_info ORDER BY id DESC LIMIT 0, 1;" > version.index
sed '1d' version.index > tmpfile; mv tmpfile version.index; cat version.index && rm version.index