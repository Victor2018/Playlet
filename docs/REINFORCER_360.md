 # 一、360加固windows 配置
1，查看签名配置
#java -jar .\jiagu.jar --config-sign-apk show
java -jar jiagu.jar -config-sign show apk

2，添加签名配置
#java -jar .\jiagu.jar --config-sign-apk add D:\GitProjects\ydj-driver-android\docs\ydj_keystore.jks android ydj android
java -jar .\jiagu.jar -config-sign add apk D:\GitProjects\ydj-driver-android\docs\ydj_keystore.jks android ydj android

3，查看加固配置（如果没有配置执行第5添加加固配置）
#java -jar .\jiagu.jar --config-jiagu-apk show
java -jar .\jiagu.jar -config-jiagu show apk
,4，删除所有加固配置
#java -jar .\jiagu.jar  --config-jiagu-apk update --clean
java -jar .\jiagu.jar -config-jiagu delete all

5，添加加固配置（所有的加固配置在360命令行加固工具help.txt中）
java -jar .\jiagu.jar --config-jiagu-apk update --x86 --cert-check --risk-env-check --crash-log --analyse --piracy --adv-env-check --name "优大集加固功能配置" --pn any

# 二、Android项目配置
1，在项目目录docs中reinforcer_360.py Python脚本中配置360命令行加固工具路径：
示例：jgPath = "D:\\soft\\360jiagubao_windows\\jiagu\\"

2，output_path 为360加固后apk保存目录，不需要修改，这里直接使用Android编译apk产物目录project_path\app\build\outputs\apk\


