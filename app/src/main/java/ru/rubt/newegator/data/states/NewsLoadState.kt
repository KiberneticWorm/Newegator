package ru.rubt.newegator.data.states

sealed class NewsLoadState {
    object NewsSuccessLoadedState: NewsLoadState()
    object NewsFailedLoadedState: NewsLoadState()
}