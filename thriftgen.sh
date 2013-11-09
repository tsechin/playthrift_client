#!/bin/bash
src_dir=src/main/java
thrift_file=src/main/thrift/mathservice.thrift
java_package=$(grep '^namespace java' ${thriftfile} | cut -d' ' -f 3)
java_package_path=$(echo ${java_package} | sed -E 's/\./\//g')

[ -d ${src_dir}/${java_package_path} ] && rm -r ${src_dir}/${java_package_path}
thrift --gen java -out ${src_dir} ${thriftfile}
