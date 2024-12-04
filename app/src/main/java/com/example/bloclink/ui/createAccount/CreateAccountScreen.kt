package com.example.bloclink.ui.createAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bloclink.ui.ConfirmPassword
import com.example.bloclink.ui.EmailTextField
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.MyButton
import com.example.bloclink.ui.MyCheckBox
import com.example.bloclink.ui.MyTextField
import com.example.bloclink.ui.PasswordTextField
import com.example.bloclink.ui.Popupbackstackbutton
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue

@Composable
fun CreateAccountScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var acceptPrivacyPolicy by remember { mutableStateOf(false) }
    var acceptCookies by remember { mutableStateOf(false) }
    var emailFailed by remember { mutableStateOf(false) }
    var passwordFailed by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordFailed by rememberSaveable { mutableStateOf(false) }
    var acceptPrivacyPolicyFailed by remember { mutableStateOf(false) }
    var acceptCookiesFailed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        /*Spacer(modifier = Modifier.fillMaxSize(0.05f))
        Popupbackstackbutton(navController = navController)*/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 40.dp, end = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter), // Alinea la columna en la parte superior del Box.
                horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos dentro de la columna.
            ) {

                LogoBlocLink()

                CreateAccountText()

                NameTextField()

                SurnameTextField()

                EmailTextField(email = email, onvaluechange = { email = it })

                PasswordTextField(password = password, onvaluechange = { password = it })

                ConfirmPassword(
                    confirmPassword = confirmPassword,
                    onvaluechange = { confirmPassword = it })

                TermsAndConditionsCheckBox(
                    onclick = {
                        if (!isValidEmail(email)) {
                            emailFailed = true
                        }
                        if (password.length < 6) {
                            passwordFailed = true
                        }
                        if (password != confirmPassword) {
                            confirmPasswordFailed = true
                        }
                        if (!acceptPrivacyPolicy) {
                            acceptPrivacyPolicyFailed = true
                        }
                        if (!acceptCookies) {
                            acceptCookiesFailed = true
                        }

                        if (!emailFailed && !passwordFailed && !confirmPasswordFailed && !acceptPrivacyPolicyFailed && !acceptCookiesFailed) {
                            /*viewModel.createUserAccount(userinput, emailinput, passinput) {
                                navController.navigate(MyScreenRoutes.PROFILE)
                            }*/
                        }
                    },
                    acceptPrivacyPolicy = acceptPrivacyPolicy,
                    acceptCookies = acceptCookies,
                    onvaluechange_privacy = { acceptPrivacyPolicy = !acceptPrivacyPolicy },
                    onvaluechange_cookies = { acceptCookies = !acceptCookies }
                )

            }
        }
    }
}

// Texto "Create an account"
@Composable
fun CreateAccountText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = "Create an account",
            fontSize = 24.sp,
            fontFamily = dmsans_regular,
        )
    }
}

// Input de nombre.
@Composable
fun NameTextField() {
    var nameError by rememberSaveable { mutableStateOf(false) }
    var nameInput by rememberSaveable { mutableStateOf("") }

    MyTextField(
        iserror = nameError,
        supportingText = "",
        data = nameInput,
        label = "Name",
        onvaluechange = { nameInput = it }
    )
}

// Input de apellido.
@Composable
fun SurnameTextField() {
    var surnameError by rememberSaveable { mutableStateOf(false) }
    var surnameInput by rememberSaveable { mutableStateOf("") }

    MyTextField(
        iserror = surnameError,
        supportingText = "",
        data = surnameInput,
        label = "Surname",
        onvaluechange = { surnameInput = it }
    )
}

// CheckBox de términos y condiciones, y cookies de uso.
@Composable
fun TermsAndConditionsCheckBox(
    onclick : () -> Unit,
    acceptPrivacyPolicy: Boolean,
    onvaluechange_privacy: (Boolean) -> Unit,
    onvaluechange_cookies: (Boolean) -> Unit,
    acceptCookies: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(    // Box que contiene el primer CheckBox (Política de privacidad y Términos y Condiciones).
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            MyCheckBox(
                checked = acceptPrivacyPolicy,
                onCheckedChange = onvaluechange_privacy ,
                label = "I acknowledge that I have read and agree to the Privacy Policy, Terms and Conditions."
            )
        }
        Box(    // Box que contiene el segundo CheckBox (Cookies).
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
        ) {
            MyCheckBox(
                checked = acceptCookies,
                onCheckedChange = onvaluechange_cookies,
                label = "I consent to the use of cookies for enhancing my experience, as described in the policy."
            )
        }
        Box(    // Botón de crear cuenta.
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp)
        ) {
            MyButton(
                fontFamily = dmsans_light,
                text = "Create account",
                textColor = Color.White,
                containerColor = lightBlue,
                borderColor = lightBlue,
                onClick = onclick,
                buttonWidthFraction = 5f,
                padding = 0.dp,
                shapeCornerRadius = 5f
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen(navController: NavController) {
    CreateAccountScreen(navController)
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return email.matches(emailRegex)
}