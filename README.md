##Android用户行为统计(Business Intelligence)


###1、日志提交时机

**wifi环境：**

+ 1、日志达到40条时，自动触发一次日志提交行为；
+ 2、进入APP时，立即上传；
+ 3、退出APP时，立即上传；
+ 4、APP切换到后台，立即上传。

**非wifi环境：**

+ 1、考虑到流量问题，非wifi网络下，当天的日志次日上传。

###2、session规则

这里的session是用户一次会话的完整生命周期(一次进入退出是一个session会话、进入后台也算一次session会话)

+ 1、sessionId的生成规则：
sessionId的生成规则： md5（设备Id+用户Id+时间戳）
+ 2、sessionId的生命周期：
进入App程序生成新的sessionId
进入后台sessionId生命周期结束
杀进程sessionId生命周期结束
+ 3、sequence：
同一sessionId下，需在客户端标识sequence（BI添加顺序），以保证到服务器端是有序的。






