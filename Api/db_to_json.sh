#!/bin/bash

db='DATABASE'
data_folder="data_database_${db}"
mkdir ${data_folder}

#echo "SHOW TABLES FROM ${db};" | mysql -u USER

touch tableName.sql

echo "USE ${db};" >> tableName.sql
echo "SHOW TABLES;" >> tableName.sql



touch tableName.txt
for table_name in $( cat tableName.sql | mysql -u USER ); do
        echo "${table_name}" >> tableName.txt
done

rm tableName.sql



sed '1d' tableName.txt > tmpfile; mv tmpfile tableName.txt

touch select.sql

for table_name in $( cat tableName.txt ); do
        echo "USE ${db}; SELECT * FROM ${table_name};" > select.sql
        cat select.sql | mysql -u USER >> ${data_folder}/${table_name}_data.txt
done
rm select.sql

for data in $( ls ${data_folder}/ ); do
        cat ${data_folder}/${data}
done

rm -r ${data_folder}/
rm tableName.txt