#!/bin/bash

BUILD_FILE_PATH=$(ls /home/ec2-user/app/build/dev-beginner-group-web/build/libs/*.jar)
BUILD_FILE_NAME=$(basename $BUILD_FILE_PATH)
echo "> build 파일명: $BUILD_FILE_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=/home/ec2-user/app/deploy/

cp $BUILD_FILE_PATH $DEPLOY_PATH

echo "> 현재 구동중인 Set 확인"
PORT_SET1=8081
PORT_SET2=8082
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc |tr ';' ' ' |cut -d':' -f3- |xargs)
echo "> $CURRENT_PORT"

# 쉬고 있는 set 찾기: set1 사용중이면 set2가 쉬고 있고, 반대면 set1이 쉬고 있음
if [ $CURRENT_PORT == $PORT_SET1 ]
then
  IDLE_SET=set2
elif [ $CURRENT_PORT == $PORT_SET2 ]
then
  IDLE_SET=set1
else
  echo "> 일치하는 Set이 없습니다. PORT: $CURRENT_PORT"
  exit
fi

echo "> application.jar 교체"
IDLE_APPLICATION=$DEPLOY_PATH$IDLE_SET-dev-beginner-group-web.jar

ln -Tfs $DEPLOY_PATH$BUILD_FILE_NAME $IDLE_APPLICATION

echo "> $IDLE_SET 에서 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(cat /home/ec2-user/app/script/$IDLE_SET-application.pid)

if [ -z $CURRENT_PID ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> $IDLE_SET 배포"
nohup java -jar -Dspring.profiles.active=$IDLE_SET $IDLE_APPLICATION 2>&1 > /home/ec2-user/app/nohup/$IDLE_SET.nohup.out &
echo "> $IDLE_SET 10초 후 Health check 시작"
echo "> curl --silent http://localhost:$CURRENT_PORT/health -H "
sleep 10

retry_count=0
while [ $retry_count -lt 20 ]; do
     response="$(curl --silent http://localhost:$CURRENT_PORT/health)"
     if [ -n "$response" ]; then
         up_count=$(echo $response | grep 'UP' | wc -l)

         if [ $up_count -ge 1 ]; then
             echo ">  Health check 성공"
             break
         else
             echo ">  Health check결과 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
             echo ">  받은 응답: ${response}"
             exit 1
         fi
     fi

     # retry_count수 증가
     let retry_count++

     if [ $retry_count -eq 20 ]; then
         >&2 echo ">  Health check 실패. Nginx에서 활성화 하지 않고 배포를 종료합니다."
         exit 1
     fi

     echo ">  Health check 연결 실패. 재시도..."
     sleep 8
done

echo "> 스위칭"
/home/ec2-user/app/script/switch.sh
