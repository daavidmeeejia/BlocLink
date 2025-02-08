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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bloclink.R
import com.example.bloclink.model.db.LoginViewModel
import com.example.bloclink.ui.EmailTextField
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.MyButton
import com.example.bloclink.ui.MyButtonWithLogo
import com.example.bloclink.ui.MyTextField
import com.example.bloclink.ui.PasswordTextField
import com.example.bloclink.ui.createAccount.isValidEmail
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
fun LoginScreen(
    navController: NavController
) {

    //  Variables recuperar contraseña.
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var failed = remember { mutableStateOf(false) }

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

            LogInText()

            EmailTextField(
                email = email,
                onvaluechange = { email = it; failed.value = false },
                emailFailed = failed,
                supportingText = "",
            )

            PasswordTextField(
                password = password,
                onvaluechange = { password = it; failed.value = false },
                passwordFailed = failed,
                supportingText = "Email and/or Password are incorrect."
            )

            RenovatePasswordButton(sheetState, scope)

            LogInButton(
                navController = navController,
                email = email,
                password = password,
                viewModel = LoginViewModel(),
                failed = failed
            )

            SocialMediaText()

            SocialMediaButtons()

            YouDontHaveAccount(navController = navController)

            if (sheetState.isVisible) {
                RenovatePasswordSheet(sheetState, scope)
            }
        }
    }
}

// Texto "Log in".
@Composable
fun LogInText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontFamily = dmsans_regular,
        )
    }
}

// Botón Log in.
@Composable
fun LogInButton(
    viewModel: LoginViewModel,
    email: String,
    password: String,
    failed: MutableState<Boolean>,
    navController: NavController
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        OutlinedButton(
            onClick = {
                if (email != "" && password != "") { //Si correo y contraseña no estan vacio
                    viewModel.signIn(email, password, profile = {
                        navController.navigate("home")
                    }, onError = {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                        failed.value = true
                    })
                } else {
                    failed.value = true
                }
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
fun SocialMediaText() {
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

    val context = LocalContext.current

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
                onClick = {
                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                },
                containerColor = darkBlue,
                borderColor = darkBlue,
                iconColor = Color.White,
                customWidth = 0.46f,
                iconId = R.drawable.google_logo_png
            )

            MyButtonWithLogo(
                text = "Twitter",
                onClick = {
                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                },
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
fun YouDontHaveAccount(navController: NavController) {
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
                    onClick = {
                        navController.navigate("createAccount")
                    },
                    buttonWidthFraction = 5f,
                    padding = 0.dp,
                    shapeCornerRadius = 5f
                )
            }
        }
    }
}

// Botón para recuperar contraseña.
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

// Modal Bottom Sheet para recuperar contraseña.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovatePasswordSheet(sheetState: SheetState, scope: CoroutineScope) {

    val context = LocalContext.current
    var error by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
        sheetState = sheetState,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            ) {

                MyTextField(
                    error = error,
                    supportingText = "Please enter your email",
                    data = email,
                    label = "Email",
                    onvaluechange = { email = it; error = false }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
            ) {
                MyButton(
                    fontFamily = dmsans_light,
                    text = "Send new password",
                    textColor = Color.White,
                    containerColor = lightBlue,
                    borderColor = lightBlue,
                    onClick = {
                        Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
                        if (!isValidEmail(email)) {
                            error = true
                        }
                    },
                    buttonWidthFraction = 5f,
                    padding = 0.dp,
                    shapeCornerRadius = 5f
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        })
}


