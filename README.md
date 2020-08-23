1.启动脚本，重启,停止


#!/bin/bash
#设置环境变量
export JAVA_HOME=/usr/local/jdk1.8.0_181
export JRE_HOME=/$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin

#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=csdn-1.0-SNAPSHOT.jar
#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh restart.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
#grep -v反选匹配 awk指定文件某一行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq 0 ]; then
    echo "${APP_NAME} is already running. pid=${pid}"
  else
    nohup java -jar ${APP_NAME} --spring.profiles.active=uat >/null 2>&1 &
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  sleep 5
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac

2.查看监控
java -Dserver.port=8888 -Dcsp.sentinel.dashboard.server=localhost:8719 -jar sentinel-dashboard-1.8.0.jar