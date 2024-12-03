package com.example.bloclink.ui.createAccount

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloclink.R
import com.example.bloclink.ui.EmailTextField
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.MyButton
import com.example.bloclink.ui.MyCheckBox
import com.example.bloclink.ui.MyTextField
import com.example.bloclink.ui.PasswordTextField
import com.example.bloclink.ui.login.darkBlue
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue

@Composable
fun CreateAccountScreen(
    //navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 40.dp, end = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter), // Alinea la columna en la parte superior del Box.
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos dentro de la columna.
        ) {

            LogoBlocLink()

            CreateAccountText()

            NameTextField()

            SurnameTextField()

            EmailTextField()

            PasswordTextField()

            ConfirmPassword()

            TermsAndConditionsCheckBox()

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

    var passerror by rememberSaveable { mutableStateOf(false) }
    var nameInput by rememberSaveable { mutableStateOf("") }

    MyTextField(
        iserror = passerror,
        supportingText = "",
        data = nameInput,
        label = "Name",
        onvaluechange = { nameInput = it },
        modifier = Modifier
            .padding(bottom = 30.dp)
    )
}

// Input de apellido.
@Composable
fun SurnameTextField() {
    var passerror by rememberSaveable { mutableStateOf(false) }
    var surnameInput by rememberSaveable { mutableStateOf("") }

    MyTextField(
        iserror = passerror,
        supportingText = "",
        data = surnameInput,
        label = "Surname",
        onvaluechange = { surnameInput = it },
        modifier = Modifier
            .padding(bottom = 30.dp)
    )
}

// Input de confirmar contraseña.
@Composable
fun ConfirmPassword() {
   PasswordTextField()
}

// CheckBox de términos y condiciones, y cookies de uso.
@Composable
fun TermsAndConditionsCheckBox() {
    var acceptPrivacyPolicy by remember { mutableStateOf(false) }
    var acceptCookies by remember { mutableStateOf(false) }

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
                onCheckedChange = { acceptPrivacyPolicy = it },
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
                onCheckedChange = { acceptCookies = it },
                label = "I consent to the use of cookies for enhancing my experience, as described in the policy."
            )
        }
        Box(    // Botón de crear cuenta.
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            MyButton(
                fontFamily = dmsans_light,
                text = "Create account",
                textColor = Color.White,
                containerColor = lightBlue,
                borderColor = lightBlue,
                onClick = {},
                buttonWidthFraction = 5f,
                padding = 0.dp,
                shapeCornerRadius = 5f
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    CreateAccountScreen()
}