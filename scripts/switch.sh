#!/bin/bash

#!/bin/bash

echo "> 현재 구동중인 Port 확인"
PORT_SET1=8081
PORT_SET2=8082
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc |tr ';' ' ' |cut -d':' -f3- |xargs)
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


echo "> Port Switch"
echo "set \$service_url http://127.0.0.1:${SWITCH_PORT};" |sudo tee /etc/nginx/conf.d/service-url.inc
# sudo sed -i "s/localhost:[0-9]*/localhost:$SWITCH_PORT/g" /etc/nginx/conf.d/service-url.inc

PROXY_PORT=$(cat /etc/nginx/conf.d/service-url.inc |tr ';' ' ' |cut -d':' -f3- |xargs)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Ngin Reload"
sudo service nginx reload