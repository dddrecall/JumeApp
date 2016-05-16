#!/system/bin/sh
#--------------------------
#脚本'Jume'资料库存放目录位置
JDIR='[jPath]'
#--------------------------
echo "m='c'" > /data/a.conf
chmod 777 /data/a.conf
$JDIR/Jume/Jume7
echo "---------------------"
echo "当前防火墙规则链："
echo "---------------------"
iptables -nL -t nat --line-number
#--------------------------
#以下为自定义脚本:

#--------------------------