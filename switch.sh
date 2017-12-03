#!/bin/bash

echo "> 현재 구동중인 Port 확인"
PORT_SET1=8081
PORT_SET2=8082
CURRENT_PORT=$(cat /etc/nginx/nginx.conf | grep '$PORT [0-9]*' | sed 's/[^0-9]//g')
echo "> Nginx Current Proxy Port: $CURRENT_PORT"

# Switch Port
if [ $CURRENT_PORT == $PORT_SET1 ]
then
  SWITCH_PORT=$PORT_SET2
elif [ $CURRENT_PORT == $PORT_SET2 ]
then
  SWITCH_PORT=$PORT_SET1
else
  echo "일치하는 Set이 없습니다. PORT: $CURRENT_PORT"
  exit
fi


sudo sed -i "s/set \$PORT [0-9]*/set \$PORT $SWITCH_PORT/g" /etc/nginx/nginx.conf

PROXY_PORT=$(cat /etc/nginx/nginx.conf | grep '$PORT [0-9]*' | sed 's/[^0-9]//g')
echo "> Nginx Proxy Port: $PROXY_PORT"

echo "> Ngin Reload"

sudo service nginx reload