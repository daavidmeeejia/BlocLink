package com.example.bloclink.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bloclink.model.classes.Company
import com.example.bloclink.ui.login.darkBlue
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue

@Composable
fun CompanyCard(company: Company, onContactClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(325.dp),

        //elevation = CardDefaults.cardElevation(defaultElevation = 2 .dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = company.image),
                contentDescription = company.companyName,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = company.companyName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = company.description,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onContactClick(company.companyId) },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = lightBlue
                ),
                border = BorderStroke(1.dp, lightBlue),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text(
                    text = "Contactar",
                    fontFamily = dmsans_regular,
                    color = Color.White
                )
            }
        }
    }
}