package com.aprouxdev.kotlinsingletonsmvvm

import com.aprouxdev.kotlinsingletonsmvvm.models.User

object ExampleSingleton  {

    val singletonUser: User by lazy {
        User("mitchelltabian@gmail.com", "mitch", "some_image_url.png")
    }
}