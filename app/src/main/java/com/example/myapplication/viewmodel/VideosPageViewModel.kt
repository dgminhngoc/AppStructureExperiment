package com.example.myapplication.viewmodel

interface IVideosPageViewModel: IViewModel {

}

class VideosPageViewModel(
    onDisposeAction: (() -> Unit)? = null
): IVideosPageViewModel, BaseViewModel(onDisposeAction = onDisposeAction) {

}