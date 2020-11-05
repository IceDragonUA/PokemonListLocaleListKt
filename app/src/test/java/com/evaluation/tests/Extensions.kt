package com.evaluation.tests

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver

fun <T> Single<T>.testAwait(): TestObserver<T> {
    return this.test().also {
        it.awaitTerminalEvent()
    }
}

fun <T> Observable<T>.testAwait(): TestObserver<T> {
    return this.test().also {
        it.awaitTerminalEvent()
    }
}

fun Completable.testAwait(): TestObserver<Void> {
    return this.test().also {
        it.awaitTerminalEvent()
    }
}

fun <T> Single<T>.test(testBlock: TestObserver<T>.() -> Unit) {
    this.testAwait().apply {
        testBlock.invoke(this)
    }
}

fun Completable.test(testBlock: TestObserver<Void>.() -> Unit) {
    this.testAwait().apply {
        testBlock.invoke(this)
    }
}

fun <T> Observable<T>.test(testBlock: TestObserver<T>.() -> Unit) {
    this.testAwait().apply {
        testBlock.invoke(this)
    }
}