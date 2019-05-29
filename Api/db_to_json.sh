#!/bin/bash

db='DATABASE'
data_folder="data_database_${db}"
mkdir ${data_folder}

#echo "SHOW TABLES FROM ${db};" | mysql -u local

touch tableName.sql

echo "USE ${db};" >> tableName.sql
echo "SHOW TABLES;" >> tableName.sql



touch tableName.txt
for table_name in $( cat tableName.sql | mysql -u USER ); do
        echo "${table_name}" >> tableName.txt
done

rm tableName.sql

rm -r ${data_folder}/
rm tableName.txt