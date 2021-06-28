package com.karl.demo.demo.vm

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import com.karl.demo.demo.app.ResultWrapper
import com.karl.demo.demo.app.request
import com.karl.demo.demo.entity.CommonEntity
import com.karl.demo.demo.entity.DivisionEntity
import com.karl.demo.demo.entity.LoginResult
import com.karl.demo.demo.model.TodoModel
import com.karl.kotlin.extension.putSP
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class ToDoViewModel @Inject constructor(
    private val toDoModel: TodoModel,
    @ApplicationContext private val context: Context
) {

    val loginState = MediatorLiveData<Boolean>()

    fun login(userName: String, password: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = request { toDoModel.login(userName, password) }
            when (result) {
                is ResultWrapper.Success<LoginResult> -> {
                    if (result.value.isSuccess) {
                        loginState.value = result.value.isSuccess
                        result.value.token?.let {
                            context.putSP("token", it)
                        }

                    }
                }
                else -> {
                }
            }
            //result.log()
        }
    }

}