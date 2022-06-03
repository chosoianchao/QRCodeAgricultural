package com.utt.qrcodeagricultural.ui.register.viewmodel

import android.util.Log
import com.google.firebase.auth.UserProfileChangeRequest
import com.utt.qrcodeagricultural.Account
import com.utt.qrcodeagricultural.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterVm @Inject constructor() : BaseViewModel() {
    fun validate(name: String, email: String, password: String): Account {
        return if (name.isEmpty()) {
            Account.ERROR_NAME
        } else if (email.isEmpty()) {
            Account.ERROR_EMAIL
        } else if (!isEmailInvalid(email)) {
            Account.INVALID_EMAIL
        } else if (password.isEmpty() || password.length <= 5) {
            Account.ERROR_PASSWORD
        } else {
            Account.SUCCESS
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        actionSuccess: () -> Unit,
        actionFailed: () -> Unit,
    ) {
        auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build()
                mUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    Log.d("Thang", "updated display name success")
                }
                insertData(name, email)
                actionSuccess()
            }
        }?.addOnFailureListener { actionFailed() }
    }

    private fun insertData(name: String, email: String) {
        val ref = mUser?.uid?.let { root?.database?.reference?.child("Users")?.child(it) }
        ref?.child("Name")?.setValue(name)
        ref?.child("Email")?.setValue(email)
    }
}
