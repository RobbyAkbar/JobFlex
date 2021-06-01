package com.exwara.jobflex.ui.account.signup

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exwara.jobflex.R
import com.exwara.jobflex.core.data.source.local.entity.UserEntity
import com.exwara.jobflex.core.utils.MyApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by robby on 31/05/21.
 */
class SignUpViewModel(private val myApplication: Application) : AndroidViewModel(myApplication) {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()

    var fullName: String = ""
    var phoneNumber: String = ""
    var email: String = ""
    var password: String = ""

    private val _fullNameError = MutableLiveData<String>()
    val fullNameError: LiveData<String>
        get() = _fullNameError

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String>
        get() = _phoneNumberError

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError

    private val _buttonEnabled = MutableLiveData<Boolean>().apply { value = true }
    val buttonEnabled: LiveData<Boolean>
        get() = _buttonEnabled

    private val _navigateToLogin = MutableLiveData<Boolean>().apply { value = false }
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _signUpError = MutableLiveData<String>()
    val signUpError: LiveData<String>
        get() = _signUpError

    fun createUser() {
        when {
            fullName.isEmpty() -> _fullNameError.value =
                myApplication.getString(R.string.name_required_error)
            email.isEmpty() -> _emailError.value =
                myApplication.getString(R.string.email_required_error)
            phoneNumber.isEmpty() -> _phoneNumberError.value =
                myApplication.getString(R.string.phone_required_error)
            password.isEmpty() -> _passwordError.value =
                myApplication.getString(R.string.password_required_error)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> _emailError.value =
                myApplication.getString(R.string.malformed_email_error)
            !Patterns.PHONE.matcher(phoneNumber).matches() -> _phoneNumberError.value =
                myApplication.getString(R.string.malformed_phone_error)
            password.length < 6 -> _passwordError.value =
                myApplication.getString(R.string.short_password_error)
            else -> {
                _buttonEnabled.value = false
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    createUserWithoutImage()
                }.addOnFailureListener {
                    it.printStackTrace()
                    _signUpError.value = it.message
                    _buttonEnabled.value = true
                }
            }
        }
    }

    private fun createUserWithoutImage() {
        val userid = auth.currentUser!!.uid
        val items = HashMap<String, Any>()
        items["email"] = email
        items["fullName"] = fullName
        items["phoneNumber"] = phoneNumber
        items["userID"] = userid
        items["profilePictureURL"] = ""
        items["active"] = true
        saveUserToDatabase(auth.currentUser!!, items)
    }

    private fun saveUserToDatabase(user: FirebaseUser, items: HashMap<String, Any>) {
        database.collection("users").document(user.uid).set(items)
            .addOnSuccessListener {
                val userEntity = UserEntity()
                userEntity.userID = user.uid
                userEntity.email = items["email"].toString()
                userEntity.fullName = items["fullName"].toString()
                userEntity.phoneNumber = items["phoneNumber"].toString()
                userEntity.profile_pic = items["profilePictureURL"].toString()
                userEntity.active = true
                MyApplication.currentUser = userEntity
                _navigateToLogin.value = true
            }.addOnFailureListener {
                _signUpError.value = it.message
                _buttonEnabled.value = true
            }
    }

    fun doneNavigating() {
        _navigateToLogin.value = false
    }
}