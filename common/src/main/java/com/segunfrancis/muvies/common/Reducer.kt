package com.segunfrancis.muvies.common

interface Reducer<A, S> {

    operator fun invoke(action: A, currentState: S): S
}
