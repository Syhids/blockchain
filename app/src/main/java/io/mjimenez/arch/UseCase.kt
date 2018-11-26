package io.mjimenez.arch

import io.reactivex.Observable

interface UseCase<Arguments, Result> {
    fun execute(arguments: Arguments): Observable<Result>
}