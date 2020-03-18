package com.aprouxdev.kotlinsingletonsmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aprouxdev.kotlinsingletonsmvvm.models.User
import com.aprouxdev.kotlinsingletonsmvvm.repository.MainRepository

class MainViewModel : ViewModel() {

    private val _userId: MutableLiveData<String> = MutableLiveData()

    // User listen to the LiveData String _userId and convert it to a User
    val user: LiveData<User> = Transformations
        .switchMap(_userId){ userId ->
            MainRepository.getUser(userId)
        }

    // LiveData transformations switchMap is the same as switchMap Observers take one object and transform to another
    //

    fun setUserId(userId: String){
        val update = userId
        if (_userId.value == update) {
            return
        }
        _userId.value = update
    }

    fun cancelJobs(){
        MainRepository.cancelJobs()
    }
}