package com.example.myapplication.viewmodel

import com.example.myapplication.domain.DataRepository
import com.example.myapplication.domain.IDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val dataRepository: IDataRepository = DataRepository()
): IViewModel() {
    private var coroutineJob: Job? = null

    fun doSomething() {
        coroutineJob = Job()
        coroutineJob?.let {
            CoroutineScope(it).launch {
               
            }
        }
    }


}