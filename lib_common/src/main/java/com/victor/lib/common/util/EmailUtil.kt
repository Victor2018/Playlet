package com.victor.lib.common.util

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EmailUtil
 * Author: Victor
 * Date: 2024/04/20 17:01
 * Description: 
 * -----------------------------------------------------------------
 */

object EmailUtil {
    fun isValidEmail(email: String): Boolean {
        var isValidEmail = false
        try {
            val emailRegex = Regex(
                "[a-zA-Z0-9+._%/-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
            )
            isValidEmail = emailRegex.matches(email)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isValidEmail
    }
}