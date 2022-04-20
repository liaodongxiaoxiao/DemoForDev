package com.karl.demo.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.karl.demo.BaseActivity
import com.karl.demo.R
import com.karl.demo.databinding.ActivityDemoTodoLoginBinding
import com.karl.demo.demo.vm.ToDoViewModel
import com.karl.demo.extesion.justStartActivity
import com.karl.kotlin.extension.getSPByKey
import com.karl.kotlin.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityDemoTodoLoginBinding>(ActivityDemoTodoLoginBinding::inflate) {
    @Inject
    lateinit var todoViewModel: ToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_demo_todo_login)
        binding.apply {
            btnLogin.setOnClickListener {
                todoViewModel.login(etUserName.text.toString(), etPassword.text.toString())
            }
        }

    }
}

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_todo_register)
    }
}

class TodoMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_todo_main)

        if (getSPByKey("token").isNullOrEmpty()) {
            toast("请先登录")
            justStartActivity(LoginActivity::class.java)
            finish()
        }
    }
}