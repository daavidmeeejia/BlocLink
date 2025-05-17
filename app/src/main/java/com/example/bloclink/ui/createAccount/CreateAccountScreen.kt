package com.example.bloclink.ui.createAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bloclink.model.db.LoginViewModel
import com.example.bloclink.ui.ConfirmPassword
import com.example.bloclink.ui.EmailTextField
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.MyButton
import com.example.bloclink.ui.MyCheckBox
import com.example.bloclink.ui.MyTextField
import com.example.bloclink.ui.PasswordTextField
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue

@Composable
fun CreateAccountScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    var nameFailed by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("") }
    var surnameFailed by rememberSaveable { mutableStateOf(false) }
    var surname by rememberSaveable { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailFailed = remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordFailed = rememberSaveable { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPasswordFailed by rememberSaveable { mutableStateOf(false) }
    var acceptPrivacyPolicy = remember { mutableStateOf(false) }
    var acceptPrivacyPolicyFailed = remember { mutableStateOf(false) }
    var acceptCookies = remember { mutableStateOf(false) }
    var acceptCookiesFailed = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sameEmail = remember { mutableStateOf(false) }
    var emailSupportingText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        /*Spacer(modifier = Modifier.fillMaxSize(0.05f))
        Popupbackstackbutton(navController = navController)*/   // Botón de volver atrás.
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
                // Llamadas a las funciones.

                LogoBlocLink()

                CreateAccountText()

                NameTextField(
                    name = name,
                    nameError = nameFailed,
                    onvaluechange = { name = it ; nameFailed = false }
                )

                SurnameTextField(
                    surname = surname,
                    surnameError = surnameFailed,
                    onvaluechange = { surname = it; surnameFailed = false }
                )


                if (emailFailed.value){
                    if(sameEmail.value){
                        emailSupportingText.value = "El email ya está en uso."
                    }
                    else{
                        emailSupportingText.value = "Introduce un email válido."
                    }
                }

                EmailTextField(
                    email = email,
                    emailFailed = emailFailed,
                    onvaluechange = { email = it; emailFailed.value = false},
                    supportingText = emailSupportingText.value,
                )

                PasswordTextField(
                    password = password,
                    passwordFailed = passwordFailed,
                    onvaluechange = { password = it; passwordFailed.value = false },
                    supportingText = "La contraseña debe tener al menos 6 caracteres."
                )

                ConfirmPassword(
                    confirmPasswordFailed = confirmPasswordFailed,
                    confirmPassword = confirmPassword,
                    onvaluechange = { confirmPassword = it; confirmPasswordFailed = false }
                )

                TermsAndConditionsCheckBox(
                    onclick = {
                        if (!isValidNoun(name) || name.isEmpty()) {
                            nameFailed = true
                        }
                        if (!isValidNoun(surname) || surname.isEmpty()) {
                            surnameFailed = true
                        }
                        if (!isValidEmail(email)) {
                            emailFailed.value = true
                        }
                        if (password.length < 6) {
                            passwordFailed.value = true
                        }
                        if (password != confirmPassword || confirmPassword.isEmpty()) {
                            confirmPasswordFailed = true
                        }
                        if (!acceptPrivacyPolicy.value) {
                            acceptPrivacyPolicyFailed.value = true
                        }
                        if (!acceptCookies.value) {
                            acceptCookiesFailed.value = true
                        }
                        if (!nameFailed && !surnameFailed && !emailFailed.value && !passwordFailed.value && !confirmPasswordFailed && !acceptPrivacyPolicyFailed.value && !acceptCookiesFailed.value) {
                            viewModel.createUserAccount(
                                name = name,
                                surname = surname,
                                email = email,
                                password = password,
                                profile = {
                                    navController.navigate("home")
                                },
                                context = context,
                                sameemail = {
                                    emailFailed.value = true
                                    sameEmail.value = true
                                })
                        }
                        if (emailFailed.value){
                            if(sameEmail.value){
                                emailSupportingText.value = "El email ya está en uso."
                            }
                            else{
                                emailSupportingText.value = "Introduce un email válido."
                            }
                        }
                    },
                    acceptPrivacyPolicy = acceptPrivacyPolicy,
                    acceptCookies = acceptCookies,
                    onvaluechange_privacy = {
                        acceptPrivacyPolicy.value =
                            !acceptPrivacyPolicy.value; acceptPrivacyPolicyFailed.value = false
                    },
                    onvaluechange_cookies = {
                        acceptCookies.value = !acceptCookies.value; acceptCookiesFailed.value =
                        false
                    },
                    acceptPrivacyPolicyFailed = acceptPrivacyPolicyFailed,
                    acceptCookiesFailed = acceptCookiesFailed
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
            text = "Crear cuenta",
            fontSize = 24.sp,
            fontFamily = dmsans_regular,
        )
    }
}

// Input de nombre.
@Composable
fun NameTextField(name: String, onvaluechange: (String) -> Unit, nameError: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MyTextField(
            error = nameError,
            supportingText = "Introduce un nombre válido.",
            data = name,
            label = "Nombre",
            onvaluechange = onvaluechange
        )
    }
}

// Input de apellido.
@Composable
fun SurnameTextField(surname: String, onvaluechange: (String) -> Unit, surnameError: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        MyTextField(
            error = surnameError,
            supportingText = "Introduce un apellido válido.",
            data = surname,
            label = "Apellido",
            onvaluechange = onvaluechange
        )
    }
}

// CheckBox de términos y condiciones, y cookies de uso.
@Composable
fun TermsAndConditionsCheckBox(
    onclick: () -> Unit,
    acceptPrivacyPolicy: MutableState<Boolean>,
    onvaluechange_privacy: (Boolean) -> Unit,
    onvaluechange_cookies: (Boolean) -> Unit,
    acceptCookies: MutableState<Boolean>,
    acceptPrivacyPolicyFailed: MutableState<Boolean>,
    acceptCookiesFailed: MutableState<Boolean>
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
                onCheckedChange = onvaluechange_privacy,
                label = "He leído y acepto la política de privacidad y los términos y condiciones.",
                error = acceptPrivacyPolicyFailed,
                errortext = "Este campo es obligatorio."
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
                label = "Acepto y consiento el uso y tratamiento de las cookies necesarias para el correcto funcionamiento de la aplicación.",
                error = acceptCookiesFailed,
                errortext = "Este campo es obligatorio."
            )
        }
        Box(    // Botón de crear cuenta.
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp)
        ) {
            MyButton(
                fontFamily = dmsans_light,
                text = "Crear cuenta",
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

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    //Esta expresion regular indica que la cadena debe tener un formato de correo electrónico válido.
    return email.matches(emailRegex)
}

fun isValidNoun(noun: String): Boolean {
    val nounRegex = "^[A-Za-zÁÉÍÓÚÜáéíóúüÑñ\\s\\-]+$".toRegex()
    //Esta expresion regular indica que en la cadena solo pueden haber letras mayúsculas y minúsculas, espacios, guiones y comas.
    return noun.matches(nounRegex)
}