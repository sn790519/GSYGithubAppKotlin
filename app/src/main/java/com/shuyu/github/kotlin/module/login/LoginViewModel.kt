package com.shuyu.github.kotlin.module.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View
import androidx.core.widget.toast
import com.shuyu.github.kotlin.R
import com.shuyu.github.kotlin.common.config.AppConfig
import com.shuyu.github.kotlin.common.utils.GSYPreference
import com.shuyu.github.kotlin.model.AccessToken
import com.shuyu.github.kotlin.repository.LoginRepository
import javax.inject.Inject

/**
 * Created by guoshuyu
 * Date: 2018-09-29
 */
class LoginViewModel @Inject constructor(val loginRepository: LoginRepository) : ViewModel() {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    val username =  ObservableField<String>()

    val password = ObservableField<String>()

    val token = MutableLiveData<AccessToken>()

    init {
        username.set(usernameStorage)
        password.set(passwordStorage)
    }

    fun login() {
        loginRepository.login(username.get()!!.trim(), password.get()!!.trim(), token)
    }

    fun onSubmitClick(view: View) {
        val username = this.username.get()
        val password = this.password.get()

        username?.apply {
            if(this.isEmpty()) {
                view.context.toast(R.string.LoginNameTip)
                return
            }
        }

        password?.apply {
            if(this.isEmpty()) {
                view.context.toast(R.string.LoginPWTip)
                return
            }
        }

        login()
        ///去主页需要finish
        ///navigationPopUpTo(view, null, R.id.action_nav_login_to_main, true)
    }
}