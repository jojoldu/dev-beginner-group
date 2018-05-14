#!/bin/bash

function find_idle_profile
{
    echo "> find_idle_profile"
    echo "> 현재 구동중인 Set 확인"
    response_code=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
    echo "> status code:  ${response_code}"

    if [ ${response_code} -ge 400 ]
    then
        echo "> $response_code 오류 페이지가 호출됐습니다."
        current_profile=set2
    else
        current_profile=$(curl -s http://localhost/profile)
    fi

    echo "> current_profile: $current_profile"

    # 쉬고 있는 set 찾기: set1이 사용중이면 set2가 쉬고 있고, 반대면 set1이 쉬고 있음
    if [ ${current_profile} == set1 ]
    then
      idle_profile=set2
    elif [ ${current_profile} == set2 ]
    then
      idle_profile=set1
    else
      echo "> 일치하는 Profile이 없습니다. Profile: $current_profile"
      idle_profile=set1
    fi

    echo "> ${idle_profile}이 반환됩니다."

    echo "${idle_profile}"
}

function find_idle_port
{
    echo "> find_idle_port"
    find_idle_profile
    idle_profile=$?

    echo "> idle_profile: $idle_profile"

    if [ ${idle_profile} == set1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}