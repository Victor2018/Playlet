package com.victor.support

import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.tooling.events.FinishEvent
import org.gradle.tooling.events.OperationCompletionListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BuildFinishService
 * Author: Victor
 * Date: 2026/11/8
 * Description: 构建结束回调服务，替代已废弃的 BuildListener#buildFinished
 * -----------------------------------------------------------------
 */

abstract class BuildFinishService : BuildService<BuildServiceParameters.None>,
    OperationCompletionListener, AutoCloseable {

    private var onFinishBlock: (() -> Unit)? = null

    fun onBuildFinish(block: () -> Unit) {
        onFinishBlock = block
    }

    /**
     * 任务执行完成回调，通过 BuildEventsListenerRegistry 注册后保证服务实例被创建
     */
    override fun onFinish(event: FinishEvent) {
        // 无需处理，构建结束的分发逻辑在 close() 中执行
    }

    override fun close() {
        onFinishBlock?.invoke()
    }
}
