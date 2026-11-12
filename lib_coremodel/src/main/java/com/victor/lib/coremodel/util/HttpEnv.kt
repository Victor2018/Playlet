package com.victor.lib.coremodel.util

enum class HttpEnv(
    val code: Int,
    val envName: String
) {
    //业务场景编码,示例值
    DEV(1, "dev"), // 开发环境
    TEST(2, "test"),//测试环境
    BETA(3, "beta"),//beta环境
    ONLINE(4, "online");//线上环境

    companion object {
        fun codeOf(code: Int): HttpEnv {
            return entries.find { it.code == code } ?: DEV
        }
        fun envNameOf(envName: String?): HttpEnv {
            return entries.find { it.envName == envName} ?: DEV
        }
    }
}