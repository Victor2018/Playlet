# coding=utf-8
# encoding = utf-8
import requests
import sys
import time

def upload_to_pgyer():
	"""
    上传到蒲公英
    :param path: 文件路径
    :param api_key: API Key
    :param install_type: 应用安装方式，值为(1,2,3)。1：公开，2：密码安装，3：邀请安装。默认为1公开
    :param password: App安装密码
    :param update_description:
    :return: 版本更新描述
    """

	print ("config-len(sys.argv) = %s" %len(sys.argv))

	apkPath = sys.argv[1]
	api_key = sys.argv[2]
	update_description = sys.argv[3]
	install_type = '1'
	password = '423099'

	print ("apkPath = %s" %apkPath)
	print ("api_key = %s" %api_key)
	print ("update_description = %s" %update_description)

	headers = {'enctype': 'multipart/form-data'}
	payload = {
		'_api_key': api_key, # API Key
		'buildType': 'android', # 需要上传的应用类型，ios 或 android
		'buildInstallType': install_type, # (选填)应用安装方式，值为(1,2,3，默认为1 公开安装)。1：公开安装，2：密码安装，3：邀请安装
		'buildPassword': password, # (选填) 设置App安装密码，密码为空时默认公开安装
		'buildUpdateDescription': update_description, # (选填) 版本更新描述，请传空字符串，或不传。
	}

	# 获取上传的 token
	try:
		response = requests.post('https://www.pgyer.com/apiv2/app/getCOSToken', data=payload, headers=headers)
		print ("get_token-response.status_code = %s" %response.status_code)
		if response.status_code == requests.codes.ok:
			json = response.json()
			_upload_url = (json["data"]["endpoint"])
			payload = (json["data"]["params"])

			print ("get_token-json = %s" %json)
	except Exception as e:
		print ("get token e = %s" %e)

	# 上传apk
	try:
		file = {'file': open(apkPath, 'rb')}
		response = requests.post(_upload_url, data=payload, files=file, headers=headers)
		print ("upload_apk-response.status_code = %s" %response.status_code)

		if response.status_code == 204:
			print ("upload_apk succes then query build info...")
			_getBuildInfo(api_key=api_key, json=json)
	except Exception as e:
		print ("upload_e = %s" %e)

def _getBuildInfo(api_key, json):
	"""
    检测应用是否发布完成，并获取发布应用的信息
    """
	try:

		time.sleep(3) #先等个几秒，上传完直接获取肯定app是还在处理中~

		response = requests.get('https://www.pgyer.com/apiv2/app/buildInfo', params={
			'_api_key': api_key,
			'buildKey': (json["data"]["params"]["key"]),
		})

		print ("get_build_info-response.status_code = %s" %response.status_code)
		if response.status_code == requests.codes.ok:
			json = response.json()
			print ("get_build_info-json = %s" %json)
			code = (json["code"])
			if code == 1247 or code == 1246: # 1246	应用正在解析、1247 应用正在发布中
				print ("retry query build info...")
				_getBuildInfo(api_key=api_key, json=json)
	except Exception as e:
		print ("get_build_info_e = %s" %e)

if __name__ == '__main__':
	upload_to_pgyer()
