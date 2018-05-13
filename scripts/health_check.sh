#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
PROFILE_SH=${ABSDIR}/profile.sh
source ${PROFILE_SH}

find_idle_port
idle_port=$?

echo "> 10초 후 Health check 시작"
echo "> curl -s http://localhost:$idle_port/health "
sleep 10

for retry_count in {1..10}
do
  response=$(curl -s http://localhost:${idle_port}/health)
  up_count=$(echo ${response} | grep 'UP' | wc -l)

  if [ ${up_count} -ge 1 ]
  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ ${retry_count} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done