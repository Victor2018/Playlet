#!/usr/bin/env python
# -*- coding: UTF-8 -*-

import os
import sys
import subprocess

#android项目根目录
project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# 360加固工具目录
jgPath = "D:\\soft\\360jiagubao_windows\\jiagu\\"
#默认360加固后apk输出目录
output_path = "D:\\GitProjects\\360_output"

#360登录用户名
login_360_name = "18500190370"
login_360_password = "taodaji123"

#签名配置
keystore_path = "%s\\docs\\ydj_keystore.jks" %project_path
#keystore_password
keystore_password = "android"
#alias
alias = "ydj"
#alias_password
alias_password = "android"

# 执行cmd命令并实时打印输出（360加固Java工具在中文Windows输出GBK编码）
def run_cmd(cmd):
	process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
	for line in process.stdout:
		try:
			print(line.decode('gbk').rstrip())
		except UnicodeDecodeError:
			print(line.decode('utf-8', errors='replace').rstrip())
	process.wait()
	return process.returncode

#360加固
def reinforceApk():
	print ("config-len(sys.argv) = %s" %len(sys.argv))
	apkPath = sys.argv[1]
	keystorePath = sys.argv[2]
	reinforceOutPath = sys.argv[3] if len(sys.argv) > 3 and len(sys.argv[3]) > 0 else output_path

	# 安装包地址
	print ("apkPath = %s" %apkPath)
	print ("reinforceOutPath = %s" %reinforceOutPath)

	if (len(apkPath) > 0):
		print ("---------------------reinforce apk start---------------------")

		#加固目录不存在自动创建
		if not os.path.exists(reinforceOutPath):
			os.makedirs(reinforceOutPath)

		# 进入加固目录
		os.chdir(jgPath)

		#登录360账号
		loginCmd = "java\\bin\\java -jar jiagu.jar -login %s %s" % (login_360_name, login_360_password)
		if run_cmd(loginCmd) != 0:
			print("---------------------login 360 failed---------------------")
			sys.exit(1)
		print("---------------------login 360 success---------------------")

		#配置签名
		apkSignCmd = "java\\bin\\java -jar jiagu.jar -config-sign add apk %s %s %s %s" % (keystore_path, keystore_password, alias, alias_password)
		if run_cmd(apkSignCmd) != 0:
			print("---------------------apk sign failed---------------------")
			sys.exit(1)
		print("---------------------apk sign success---------------------")

        #加固并自动签名
		cmd = "java\\bin\\java -jar jiagu.jar -jiagu apk %s %s -auto-sign" % (apkPath, reinforceOutPath)
		if run_cmd(cmd) == 0:
			print("---------------------reinforce apk success，out apk here---------------------")
			print("reinforceOutPath = %s" %reinforceOutPath)
		else:
			print("---------------------reinforce apk failed---------------------")
	else:
		print("---------------------reinforce apk failed not found apk---------------------")

if __name__ == "__main__":
    reinforceApk()