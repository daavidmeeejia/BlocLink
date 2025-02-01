package com.example.bloclink.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

@Composable
fun ChatScreen(navController: NavController, userId: String) {
    val db = FirebaseFirestore.getInstance()
    val messages = remember { mutableStateListOf<Pair<String, String>>() }
    var messageText by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        db.collection("mensajes")
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { (user, text) ->
                Text("$user: $text", modifier = Modifier.padding(8.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                sendMessage(userId, messageText.text)
                messageText = TextFieldValue()
            }) {
                Text("Enviar")
            }
        }
    }
}

fun sendMessage(userId: String, message: String) {
    if (message.isBlank()) return

    val db = FirebaseFirestore.getInstance()
    val chatMessage = hashMapOf(
        "userId" to userId,
        "message" to message,
        "timestamp" to Date()
    )

    db.collection("mensajes")
        .add(chatMessage)
        .addOnSuccessListener { }
        .addOnFailureListener { e ->
            e.printStackTrace()
        }
}
