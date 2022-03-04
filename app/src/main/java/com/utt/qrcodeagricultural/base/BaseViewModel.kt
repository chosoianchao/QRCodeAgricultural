package com.utt.qrcodeagricultural.base

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class BaseViewModel : ViewModel() {
    protected val root: DatabaseReference? by lazy { FirebaseDatabase.getInstance().reference }
}
