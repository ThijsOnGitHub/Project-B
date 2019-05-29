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

### GETTING DATA FROM A SPECIFIC TABLE IN JSON FORMAT ###
for table_name in $( cat tableName.txt ); do
        echo "SELECT JSON_ARRAYAGG(JSON_OBJECT(" > select.sql; count=0
        for field_name in $( cat ${data_folder}/${table_name}${extention_fields}.txt ); do
                if [[ ${count} -eq 0 ]]; then
                        echo "\"${field_name}\", ${field_name}" >> select.sql
                        count=1
                else
                        echo ",\"${field_name}\", ${field_name}" >> select.sql
                fi
        done
        echo ")) FROM ${db}.${table_name};" >> select.sql
        cat select.sql | mysql -u ${mysql_user} > ${data_folder}/${table_name}${extention_data}_JSON.txt
done
rm select.sql

### PREPAIRING DATA ###
for table_name in $( cat tableName.txt ); do
        char=$(head -c 2 ${data_folder}/${table_name}${extention_data}_JSON.txt);
        while [[ ${char} != "[{" ]]; do
                sed '1d' ${data_folder}/${table_name}${extention_data}_JSON.txt > tmpfile; mv tmpfile ${data_folder}/${table_name}${extention_data}_JSON.txt
                char=$(head -c 2 ${data_folder}/${table_name}${extention_data}_JSON.txt);
        done

        echo "{ \"${table_name}\": " > tmpfile && cat ${data_folder}/${table_name}${extention_data}_JSON.txt >> tmpfile && echo " }" >> tmpfile
        mv tmpfile ${data_folder}/${table_name}${extention_data}_JSON.txt
done

#for x in $( ls ${data_folder} | grep JSON ); do
#       cat ${data_folder}/${x};
#done

jq -s 'reduce .[] as $item ({}; . * $item)' ${data_folder}/*${extention_data}_JSON.txt > ${db}.json
cat ${db}.json

rm -r ${data_folder}/; rm tableName.txt

