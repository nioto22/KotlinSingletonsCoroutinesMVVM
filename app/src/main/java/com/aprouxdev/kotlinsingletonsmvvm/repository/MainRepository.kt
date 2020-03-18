package com.aprouxdev.kotlinsingletonsmvvm.repository

import androidx.lifecycle.LiveData
import com.aprouxdev.kotlinsingletonsmvvm.api.RetrofitBuilder
import com.aprouxdev.kotlinsingletonsmvvm.models.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object MainRepository {

    var job: CompletableJob? = null

    fun getUser(userId: String) : LiveData<User>{
        job = Job()  // Create a new Job
        return object : LiveData<User>(){
            override fun onActive() {
                super.onActive()  // Means that when getUser is call, liveData<User> comes active
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {  // IO = in back onThread : kotlinx.coroutines.Dispatchers.IO
                        // -> get the user object from network on back thread
                        val user = RetrofitBuilder.apiService.getUser(userId)
                        withContext(Main) {// -> send it to the main thread as liveData
                            value = user
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }
}