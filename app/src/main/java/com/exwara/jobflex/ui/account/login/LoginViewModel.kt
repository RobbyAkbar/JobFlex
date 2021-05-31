package com.exwara.jobflex.ui.account.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exwara.jobflex.R
import com.exwara.jobflex.core.data.source.local.entity.UserEntity
import com.exwara.jobflex.core.utils.FirestoreUtil
import com.exwara.jobflex.core.utils.MyApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by robby on 31/05/21.
 */
class LoginViewModel(private val myApplication: Application) : AndroidViewModel(myApplication) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var email: String = ""
    var password: String = ""

    private val _buttonEnabled = MutableLiveData<Boolean>().apply { value = true }
    val buttonEnabled: LiveData<Boolean>
        get() = _buttonEnabled

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError

    private val _errorString = MutableLiveData<String>()
    val errorString: LiveData<String>
        get() = _errorString

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    fun login() {
        when {
            email.isEmpty() -> _emailError.value =
                myApplication.getString(R.string.email_required_error)
            password.isEmpty() -> _passwordError.value =
                myApplication.getString(R.string.password_required_error)
            else -> {
                onStartLoading()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        getFirebaseUserData()
                    } else {
                        onFinishLoading()
                        _errorString.value = it.exception?.message
                    }
                }
            }
        }
    }

    private fun getFirebaseUserData() {
        val ref = db.collection("users").document(mAuth.currentUser!!.uid)
        ref.get().addOnSuccessListener {
            val userInfo = it.toObject(UserEntity::class.java)
            MyApplication.currentUser = userInfo
            MyApplication.currentUser!!.active = true
            FirestoreUtil.updateUser(MyApplication.currentUser!!) {

            }
            onFinishLoading()
            startHomeNavigation()
        }.addOnFailureListener {
            onFinishLoading()
            _errorString.value = it.message
        }

    }

    private fun startHomeNavigation() {
        _navigateToHome.value = true
    }

    fun doneHomeNavigation() {
        _navigateToHome.value = false
    }

    private fun onStartLoading() {
        _buttonEnabled.value = false
        _progress.value = true
    }

    private fun onFinishLoading() {
        _buttonEnabled.value = true
        _progress.value = false
    }

}