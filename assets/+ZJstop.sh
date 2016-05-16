#!/system/bin/sh
#--------------------------
#脚本'Jume'资料库存放目录位置
JDIR='[jPath]'
#--------------------------
iptables -t nat -F OUTPUT
iptables -t mangle -F OUTPUT
iptables -t nat -F PREROUTING
echo "m='b'" > /data/a.conf
chmod 777 /data/a.conf
$JDIR/Jume/Jume7
#--------------------------
#以下为自定义脚本:

#--------------------------