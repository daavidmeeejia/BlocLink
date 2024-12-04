package com.example.bloclink.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bloclink.R
import com.example.bloclink.ui.login.darkBlue
import com.example.bloclink.ui.login.dmsans_extralight
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue
import kotlinx.coroutines.launch

@Composable
fun MyButtonWithLogo(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    borderColor: Color,
    iconColor: Color,
    customWidth: Float,
    iconId: Int
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = iconColor
        ),

        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 8.dp, start = 0.dp, end = 0.dp)
            .fillMaxWidth(customWidth)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
            )
            Text(
                text = text,
                fontFamily = dmsans_light,
                color = Color.White
            )
        }

    }
}

// Imagen logo BlocLink.
@Composable
fun LogoBlocLink() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bloclink_png_logo),
            contentDescription = "BlocLink Logo",
            modifier = Modifier
                .padding(top = 30.dp, bottom = 25.dp)
        )
    }
}

// Botón retroceder
@Composable
fun Popupbackstackbutton(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

// Email.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    email: String,
    onvaluechange: (String) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = email,
            onValueChange = onvaluechange,
            label = { Text("Email") },
            placeholder = {
                Text(
                    "example@email.com",
                    color = darkBlue,
                    fontFamily = dmsans_light
                )
            },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors( // Experimental
                containerColor = Color.Transparent,
                cursorColor = lightBlue,
                focusedTextColor = darkBlue,
                unfocusedTextColor = darkBlue,
                focusedLabelColor = lightBlue,
                unfocusedLabelColor = lightBlue,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedSupportingTextColor = Color.Red,
                unfocusedSupportingTextColor = Color.Red
            )
        )
    }
}


// Contraseña.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    onvaluechange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        var passwordIsVisible by rememberSaveable { mutableStateOf(false) }
        val icon = if (!passwordIsVisible) { // Variable que indica el estado del boolean.
            painterResource(id = R.drawable.opened_eye)
        } else {
            painterResource(id = R.drawable.closed_eye)
        }

        TextField(
            value = password,
            onValueChange = onvaluechange,
            label = { Text("Password") },
            placeholder = { Text("") },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors( // Experimental
                containerColor = Color.Transparent,
                cursorColor = lightBlue,
                focusedTextColor = darkBlue,
                unfocusedTextColor = darkBlue,
                focusedLabelColor = lightBlue,
                unfocusedLabelColor = lightBlue,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedSupportingTextColor = Color.Red,
                unfocusedSupportingTextColor = Color.Red
            ),
            visualTransformation = if (passwordIsVisible) { //  Si el boolean esta en true, las letras seran legibles, por el contrario, se va a aplicar una transformación.
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = { // Botón para mostrar u ocultar los caracteres de la contraseña.
                IconButton(onClick = { passwordIsVisible = !passwordIsVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Show Password",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPassword(
    confirmPassword: String,
    onvaluechange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        var passwordIsVisible by rememberSaveable { mutableStateOf(false) }
        val icon = if (!passwordIsVisible) { // Variable que indica el estado del boolean.
            painterResource(id = R.drawable.opened_eye)
        } else {
            painterResource(id = R.drawable.closed_eye)
        }

        TextField(
            value = confirmPassword,
            onValueChange = onvaluechange,
            label = { Text("Confirm password") },
            placeholder = { Text("") },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors( // Experimental
                containerColor = Color.Transparent,
                cursorColor = lightBlue,
                focusedTextColor = darkBlue,
                unfocusedTextColor = darkBlue,
                focusedLabelColor = lightBlue,
                unfocusedLabelColor = lightBlue,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedSupportingTextColor = Color.Red,
                unfocusedSupportingTextColor = Color.Red
            ),
            visualTransformation = if (passwordIsVisible) { //  Si el boolean esta en true, las letras seran legibles, por el contrario, se va a aplicar una transformación.
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = { // Botón para mostrar u ocultar los caracteres de la contraseña.
                IconButton(onClick = { passwordIsVisible = !passwordIsVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Show Password",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun MyButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color = lightBlue,
    borderColor: Color = lightBlue,
    textColor: Color = Color.White,
    fontFamily: FontFamily? = null,
    shapeCornerRadius: Float = 5f,
    padding: Dp = 8.dp,
    buttonWidthFraction: Float = 1f,
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(shapeCornerRadius.dp),
        modifier = Modifier
            .padding(top = padding, start = padding, end = padding)
            .fillMaxWidth(buttonWidthFraction)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontFamily = fontFamily,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    iserror: Boolean,
    supportingText: String,
    data: String,
    label: String,
    onvaluechange: (String) -> Unit
) {
    TextField(
        value = data,
        onValueChange = onvaluechange,
        label = {
            Text(text = label)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors( // Experimental
            containerColor = Color.Transparent,
            cursorColor = lightBlue,
            focusedTextColor = darkBlue,
            unfocusedTextColor = darkBlue,
            focusedLabelColor = lightBlue,
            unfocusedLabelColor = lightBlue,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            focusedSupportingTextColor = Color.Red,
            unfocusedSupportingTextColor = Color.Red
        ),
        supportingText = {
            if (iserror) {
                Text(text = supportingText)
            }
        }
    )
}

@Composable
fun MyCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically  // Centra la casilla de CheckBox con el texto.
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = darkBlue,
                uncheckedColor = darkBlue,
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        /*ClickableText(
            modifier = Modifier
                .padding(top = 12.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.DarkGray,
                        textDecoration = TextDecoration.None,
                        fontFamily = dmsans_extralight
                    )
                ) {
                    append(label)
                    addStringAnnotation(
                        tag = "",
                        annotation = "",
                        start = 0,
                        end = length
                    )
                }
            },
            onClick = {
                Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
            },
            style = TextStyle(
                fontSize = 14.sp
            )
        )*/

        Text(
            text = label,
            fontFamily = dmsans_extralight,
            color = Color.DarkGray
        )
    }
}