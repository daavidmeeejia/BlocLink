package com.example.bloclink.model.db

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloclink.model.classes.User
import com.example.bloclink.ui.createAccount.isValidEmail
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signIn(email: String, password: String, profile: () -> Unit, onError: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    Log.d("login", "signIn: successful")
                    profile()
                }.addOnFailureListener {
                    onError()
                }
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.w("login", "signIn: ${e.message}")
            }
        }

    fun createUserAccount(
        name: String,
        surname: String,
        email: String,
        password: String,
        context: android.content.Context,
        profile: () -> Unit,
        sameemail: () -> Unit
    ){
        var issuccessfull = false
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            Log.d("create_user", "create_user: User created successfully")
                            createUser(name, surname, email)
                            issuccessfull = true
                            profile()
                        } else {
                            Log.d("create_user", "create_user : ${task.result}")
                            issuccessfull = false
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }catch (e: Exception){
                        if (e is RuntimeExecutionException) {
                            //Log.e("create_user", "create_user : ${e.message}")
                            issuccessfull = false
                            sameemail()
                            //Toast.makeText(context, "Email already in use", Toast.LENGTH_SHORT).show()
                       }
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(name: String, surname: String, email: String) {
        val userId = auth.currentUser?.uid
        val user = User(
            userId = userId.toString(),
            name = name,
            surname = surname,
            email = email
        ).toMap()
        FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
            Log.d("users", "createUser: Display name ${it.id} created successfully")
        }.addOnFailureListener {
            Log.d("users", "createUser: Unexpected error creating user $it")
        }
    }

    fun resetpassword(email: String) {
        if (isValidEmail(email)) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("reset_password", "Email sent.")
                    }
                }
        }
    }
}