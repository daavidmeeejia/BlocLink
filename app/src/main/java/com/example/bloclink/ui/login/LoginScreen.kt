package com.example.bloclink.ui.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloclink.R
import com.example.bloclink.ui.EmailTextField
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.MyButton
import com.example.bloclink.ui.MyButtonWithLogo
import com.example.bloclink.ui.PasswordTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Colores BlocLink
var darkBlue = Color(0xFF0E2442) // Azul oscuro
val lightBlue = Color(0xFF4682B4) // Azul claro

// Fuentes BlocLink
val dmsans_extralight = FontFamily(Font(R.font.dmsans_extralight))
val dmsans_light = FontFamily(Font(R.font.dmsans_light))
val dmsans_regular = FontFamily(Font(R.font.dmsans_regular))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    //  Variables recuperar contraseña.
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

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
            //  Llamadas a las funciones.

            LogoBlocLink()

            LogInTextField()

            EmailTextField()

            PasswordTextField()

            RenovatePasswordButton(sheetState, scope)

            //RenovatePasswordSheet(sheetState, scope)

            LogInButton()

            SocialMediaTextField()

            SocialMediaButtons()

            YouDontHaveAccount()
        }
    }
}


// Texto "Log in".
@Composable
fun LogInTextField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Log in",
            fontSize = 24.sp,
            fontFamily = dmsans_regular,
        )
    }
}


// Botón Log in.
@Composable
fun LogInButton() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        OutlinedButton(
            onClick = {

                /*if (userinput == "pitopato" && passinput == "1234") { //Si correo y contraseña no estan vacio
                    Toast.makeText("Login successuful", context, Toast.LENGTH_LONG).show()
                } else {
                    error = true //Si correo y contraseña estan vacios ponemos el boolean error en true

                },*/
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = lightBlue,
                contentColor = lightBlue
            ),
            border = BorderStroke(1.dp, lightBlue),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Log in",
                fontFamily = dmsans_regular,
                color = Color.White
            )
        }
    }
}

// Texto "Continue with social media".
@Composable
fun SocialMediaTextField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 34.dp)
    ) {
        Text(
            text = "Continue with social media:",
            fontSize = 14.sp,
            fontFamily = dmsans_regular,
            color = darkBlue,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

//  Botones de las RRSS
@Composable
fun SocialMediaButtons() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            MyButtonWithLogo(
                text = "Google",
                onClick = { /* Acción del botón */ },
                containerColor = darkBlue,
                borderColor = darkBlue,
                iconColor = Color.White,
                customWidth = 0.46f,
                iconId = R.drawable.google_logo_png
            )

            MyButtonWithLogo(
                text = "Twitter",
                onClick = { /* Acción del botón */ },
                containerColor = darkBlue,
                borderColor = darkBlue,
                iconColor = Color.White,
                customWidth = 1f,
                iconId = R.drawable.twitter_logo_png
            )
        }
    }
}

// Opción de crear una cuenta.
@Composable
fun YouDontHaveAccount() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp)
    ) {
        // Línea divisoria
        Divider(
            color = Color.LightGray,
            thickness = 1.3.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Separación de la linea divisoria con el contenido.
        )

        // Contenido del Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontFamily = dmsans_regular,
                    color = darkBlue,
                    fontSize = 14.sp
                )
                MyButton(
                    fontFamily = dmsans_light,
                    text = "Sign up",
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovatePasswordButton(
    sheetState: SheetState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = darkBlue,
                        textDecoration = TextDecoration.None,
                        fontFamily = dmsans_regular
                    )
                ) {
                    append("Forgot your password?")
                    addStringAnnotation(
                        tag = "",
                        annotation = "",
                        start = 0,
                        end = length
                    )
                }
            },
            onClick = {
                scope.launch {
                    sheetState.show()
                }
            },
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovatePasswordSheet(sheetState: SheetState, scope: CoroutineScope) {
    val error = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        content = {
            Textfield(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                iserror = error.value,
                supportingText = "Enter a valid email",
                data = email.value,
                label = "Email",
                onvaluechange = { email.value = it; error.value = false })
            Spacer(modifier = Modifier.height(12.dp))
            OutlineButton(
                text = "Reset Password",
                onclick = {
                    if (email.value.isNullOrBlank() || !isValidEmail(email.value)) {
                        error.value = true
                    }else{
                        loginViewModel.resetpassword(email.value)
                    }
                },
                containercolor = MaterialTheme.colorScheme.primary,
                bordercolor = MaterialTheme.colorScheme.primary,
                textcolor = Color.White
            )
            Spacer(modifier = Modifier.height(50.dp))

        })
}*/


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}