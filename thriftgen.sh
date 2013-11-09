#!/bin/bash
src_dir=src/main/java
thrift_file=src/main/thrift/mathservice.thrift
java_package=$(cat ${thrift_file} | grep '^namespace java' | cut -d' ' -f 3)
echo "thrift IDL ${thrift_file}, java package ${java_package}"
java_package_path=$(echo ${java_package} | sed -E 's/\./\//g')
echo "java package path ${java_package_path}"

[ -d ${src_dir}/${java_package_path} ] && rm -r ${src_dir}/${java_package_path}
thrift --gen java -out ${src_dir} ${thrift_file}
