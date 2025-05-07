package com.example.bloclink.model.db

import com.example.bloclink.model.classes.Particular
import com.example.bloclink.model.classes.User

sealed class DataState {
    class Success(val data: MutableList<Particular>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}

sealed class UserState {
    class Success(val data: User) : UserState()
    class Failure(val message: String) : UserState()
    object Loading : UserState()
    object Empty : UserState()
}