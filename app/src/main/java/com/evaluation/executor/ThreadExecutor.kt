package com.evaluation.executor

import io.reactivex.Scheduler

abstract class ThreadExecutor {

    abstract val mainExecutor: Scheduler

    abstract val postExecutor: Scheduler

}