#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

deploy_path=/home/ec2-user/app
build_path=$(ls ${deploy_path}/*.jar)
jar_name=$(basename ${build_path})
echo "> build 파일명: $jar_name"

echo "> build 파일 복사"
jar_group_path=/home/ec2-user/jar/
cp ${build_path} ${jar_group_path}

idle_profile=$(find_idle_profile)

echo "> idle_profile: $idle_profile"
echo "> application.jar 교체"
idle_application_name=${idle_profile}-application.jar
echo "> idle_application_name: $idle_application_name"

idle_application_full_path=${jar_group_path}${idle_application_name}
echo "> idle_application_full_path: $idle_application_full_path"

ln -Tfs ${jar_group_path}${jar_name} ${idle_application_full_path}

echo "> $idle_profile 에서 구동중인 애플리케이션 pid 확인"
idle_pid=$(pgrep -f ${idle_application_name})

if [ -z ${idle_pid} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $idle_pid"
  kill -15 ${idle_pid}
  sleep 5
fi

echo "> $idle_profile 배포"
nohup java -jar -Dspring.profiles.active=${idle_profile} ${idle_application_full_path} > /dev/null 2> /dev/null < /dev/null &