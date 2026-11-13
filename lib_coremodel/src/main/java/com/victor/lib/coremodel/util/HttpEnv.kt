package com.victor.lib.coremodel.util

/**
 * HTTP 环境编码
 * 注意：code 需与 build-logic 侧 com.victor.support.HttpEnv 保持一致（由 configChannel 注入 HTTP_ENV_CODE）
 */
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
            return entries.find { it.envName == envName } ?: DEV
        }
    }
}
