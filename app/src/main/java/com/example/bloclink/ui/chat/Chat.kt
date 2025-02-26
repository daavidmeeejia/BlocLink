package com.example.bloclink.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bloclink.R
import com.example.bloclink.model.classes.Company
import com.example.bloclink.ui.ChatHeader
import com.example.bloclink.ui.login.lightBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class)
@Composable
fun ChatScreen(navController: NavController, userId: String, company: Company) {
    val db = FirebaseFirestore.getInstance()
    val messages = remember { mutableStateListOf<Pair<String, String>>() }
    var messageText by remember { mutableStateOf(TextFieldValue()) }
    val chatScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Nombre de la empresa como clave en Firestore
    val chatPath = "mensajes/${company.companyName}/${FirebaseAuth.getInstance().currentUser?.uid}"

    LaunchedEffect(Unit) {
        db.collection(chatPath)
            .orderBy("timestamp")
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener
                messages.clear()
                snapshots?.forEach { doc ->
                    val user = doc.getString("userId") ?: ""
                    val text = doc.getString("message") ?: ""
                    messages.add(Pair(user, text))
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ChatHeader(navController = navController, company = company)
        HorizontalDivider(
            color = Color.Black,
            thickness = 0.9.dp,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            state = chatScrollState,
            modifier = Modifier
                .weight(1f) // Hace que esta parte sea la desplazable
                .padding(start = 16.dp)

        ) {
            items(messages) { (user, text) ->
                Text("$user: $text", modifier = Modifier.padding(8.dp))
            }
        }

        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                chatScrollState.animateScrollToItem(messages.size - 1)
            }
        }

        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .fillMaxWidth(0.84f)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(50))
                        .background(Color.White, shape = RoundedCornerShape(50))
                        .height(53.dp),
                    placeholder = { Text("Escribe un mensaje...") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp),
                    shape = RoundedCornerShape(50)
                )

                Spacer(modifier = Modifier.width(15.dp))

                IconButton(
                    onClick = {
                        scope.launch {
                            sendMessage(
                                message = messageText.text,
                                company = company,
                                userId = userId
                            )
                            messageText = TextFieldValue()
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(lightBlue, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Enviar mensaje",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


fun sendMessage(userId: String, message: String, company: Company) {
    if (message.isBlank()) return

    val db = FirebaseFirestore.getInstance()
    val chatPath = "mensajes/${company.companyName}/${FirebaseAuth.getInstance().currentUser?.uid}"

    val chatMessage = hashMapOf(
        "userId" to userId,
        "message" to message,
        "timestamp" to Date()
    )

    // Verifica si la colección ya existe
    db.collection("mensajes").document(company.companyName).get()
        .addOnSuccessListener { document ->
            if (!document.exists()) {
                // Si la colección no existe, crea un documento vacío en "mensajes"
                db.collection("mensajes").document(company.companyName)
                    .set(hashMapOf("createdAt" to Date()))
                    .addOnSuccessListener {
                        // Luego de crearla, agrega el mensaje
                        db.collection(chatPath).add(chatMessage)
                    }
            } else {
                // Si la colección ya existe, solo agrega el mensaje
                db.collection(chatPath).add(chatMessage)
            }
        }
        .addOnFailureListener { e ->
            e.printStackTrace()
        }
}


