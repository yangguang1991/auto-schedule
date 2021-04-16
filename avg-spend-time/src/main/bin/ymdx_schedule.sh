#!/bin/bash

if [ "$1" != "start"  -a  "$1" != "stop"  -a  "$1" != "status"  -a  "$1" != "restart"     ]; then
  echo "Usage: sh ymdx_schedule.sh  {start|stop|status|restart}"
  exit 1
fi

source  /etc/profile

jarDir=/home/ymdxtest/yangguang/spark/ymdx-schedule/lib
serviceName='ymdx-schedule'
jarFile=${jarDir}/avg-spend-time.jar

startService(){

  ps -ef|grep -v grep|grep $jarFile    1>/dev/null 2>&1

        if [ $? -eq 0 ];then

                        echo "$serviceName is already started"

        else

        echo -n "Starting the $serviceName service......"

        allJars="$(find   ${jarDir}  -name '*.jar' | paste -d : -s)"
   
        java    -Denv=dev   -cp     $allJars  com.example.spark.main.ComputeAvgSpendTimeMain  >/dev/null 2>&1 &


        sleep  10

                ps -ef|grep -v grep|grep $jarFile 1>/dev/null 2>&1

                if [ $? -eq 0 ];then

                                echo "done"

                else

                                echo "failure"

                fi

        fi
}


stopService(){

   echo -n "Shutting down the $serviceName service......"

        ps -ef|grep -v grep|grep $jarFile    1>/dev/null 2>&1

        if [ $? -eq 0 ];then

                        num=`ps -ef|grep -v grep|grep $jarFile|awk '{printf $2" "}'`

                        kill -9 $num
                        echo "done"

        else

                        echo "is not start"

        fi

}


case $1 in
start)
 startService


;;
stop)
 stopService

  ;;
status)
  echo -n "Checking for the $serviceName ......"

    ps -ef|grep -v grep|grep  $jarFile   1>/dev/null 2>&1
	if [ $? -eq 0 ];then
		echo " is running"
	else
		echo " is not start"
	fi

  ;;



restart)

 echo  "restart for the $serviceName ......"
   stopService

  sleep  10

   startService

;;


esac
