package com.victor.lib.coremodel.bizparser

import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import org.victor.http.lib.bizparser.NormalBusinessResponseParser
import org.victor.http.lib.bizparser.BusinessResponseParser


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpBusinessResponseParser
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description: 项目统一业务响应解析器
 * -----------------------------------------------------------------
 */
object HttpBusinessResponseParser :
    BusinessResponseParser<BaseRes<*>, BaseRes<*>> by
    NormalBusinessResponseParser(
        isBusinessSuccess = { it.code == 0 },
        businessCode = { "${it.code}" },
        businessMessage = { it.message },
        result = { it }
    )
