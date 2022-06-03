package com.utt.qrcodeagricultural.ui.login.viewmodel

import com.utt.qrcodeagricultural.Account
import com.utt.qrcodeagricultural.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor() : BaseViewModel() {
    fun validate(email: String, password: String): Account {
        return if (email.isEmpty()) {
            Account.ERROR_EMAIL
        } else if (!isEmailInvalid(email)) {
            Account.INVALID_EMAIL
        } else if (password.isEmpty() || password.length <= 5) {
            Account.ERROR_PASSWORD
        } else {
            Account.SUCCESS
        }
    }

    fun login(
        email: String,
        password: String,
        actionSuccess: () -> Unit,
        actionFailed: () -> Unit,
    ) {
        auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                actionSuccess()
            }
        }?.addOnFailureListener { actionFailed() }
    }

    fun accountExists(action: () -> Unit) {
        if (mUser != null) {
            action()
        }
    }
}