package com.example.bloclink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bloclink.ui.chat.ChatScreen
import com.example.bloclink.ui.createAccount.CreateAccountScreen
import com.example.bloclink.ui.home.HomeScreen
import com.example.bloclink.ui.login.LoginScreen
import com.example.bloclink.ui.theme.BlocLinkTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()


                BlocLinkTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "login", builder = {
                            composable("login"){
                                LoginScreen(navController = navController)
                            }
                            composable("createAccount"){
                                CreateAccountScreen(navController = navController)
                            }
                            composable("home"){
                                HomeScreen(navController = navController)
                            }
                            composable("chat"){
                                Firebase.auth.currentUser!!.email?.let { it1 -> ChatScreen(userId = it1, navController = navController) }
                            }
                        })
                    }
                }
            }
        }
    }

