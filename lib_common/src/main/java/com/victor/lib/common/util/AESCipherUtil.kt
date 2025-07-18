package com.victor.lib.common.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES加解密工具类
 */
object AESCipherUtil {

    /***
     * key和iv值可以随机生成,确保与前端的key,iv对应
     */
    private const val KEY = "sdfbJKSDHG6468564_AASSFD"
    private const val IV = "SDLdsjg53465SDGS"

    /**
     * 加密
     */
    fun encrypt(data: String?, key: String, iv: String): String? {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(data?.toByteArray(Charsets.UTF_8))
            Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 解密
     */
    fun desEncrypt(data: String?, key: String, iv: String): String? {
        return try {
            val encrypted1 = Base64.decode(data, Base64.DEFAULT)
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val original = cipher.doFinal(encrypted1)
            String(original, Charsets.UTF_8).trim { it <= ' ' }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 解密方法
     */
    fun desEncrypt(data: String?): String? {
        return desEncrypt(data, KEY, IV)
    }
}
