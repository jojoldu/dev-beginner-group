#!/bin/bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

idle_port=$(find_idle_port)

echo "> 전환할 Port: $idle_port"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${idle_port};" |sudo tee /etc/nginx/conf.d/service-url.inc

current_profile=$(curl -s http://localhost/profile)
echo "> Nginx Current Profile : $current_profile"

echo "> Nginx Reload"
sudo service nginx reload