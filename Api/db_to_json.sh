#!/bin/bash

db='DATABASE'
mysql_user="USER"

data_folder="data_database_${db}"
extention_data="_data"
extention_field="_field"
extention_data_fields="_data_field"

### PREPAIR PROGRAM ###
mkdir ${data_folder}

if $( hash jq ); then
        echo "-------------------------------- GENERATING JSON --------------------------------"
else
        apt-get install jq
        echo "-------------------------------- GENERATING JSON --------------------------------"
fi

### GETTING DB TABLES ###
touch tableName.sql; echo "USE ${db}; SHOW TABLES;" >> tableName.sql

touch tableName.txt
for table_name in $( cat tableName.sql | mysql -u ${mysql_user} ); do
        echo "${table_name}" >> tableName.txt
done

rm tableName.sql; sed '1d' tableName.txt > tmpfile; mv tmpfile tableName.txt

### GETTING TABLE FIELDS ###
for table_name in $( cat tableName.txt ); do
        echo "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name = '${table_name}' AND table_schema = '${db}';" > select.sql
        cat select.sql | mysql -u ${mysql_user} >> ${data_folder}/${table_name}${extention_fields}.txt
        sed '1d' ${data_folder}/${table_name}${extention_fields}.txt > tmpfile; mv tmpfile ${data_folder}/${table_name}${extention_fields}.txt
done
rm select.sql


rm -r ${data_folder}/; rm tableName.txt

