package com.example.bloclink.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bloclink.model.classes.Company
import com.example.bloclink.model.classes.Particular
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.login.lightBlue

@Composable
fun CompanyCard(
    company: Company = Company(),
    particular: Particular = Particular(),
    onCompanyContactClick: (Company) -> Unit = {},
    onParticularContactClick: (Particular) -> Unit = {}
) {
    //Metodo multiclase: Si Company es una clase vacia (por default), pilla particular.
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
            Box(
                modifier = Modifier
                    .size(75.dp)

                    .clip(shape = CircleShape)
            ) {
                if (particular.image.toString() != "") {
                    /*AsyncImage(
                        model = particular.image.toString(),
                        contentDescription = particular.particularName.toString(),
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = CircleShape)
                            .background(color = Color.White),
                        contentScale = ContentScale.Crop,
                    )*/
                    Image(
                        painter = painterResource(
                            id = particular.image.toString().toInt(),
                        ),
                        contentDescription = if (company.companyName != "") {
                            company.companyName
                        } else {
                            particular.particularName
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = CircleShape)
                            .background(color = Color.White)
                    )
                } else {
                    Image(
                        painter = painterResource(
                            id = company.image.toString().toInt(),
                        ),
                        contentDescription = if (company.companyName != "") {
                            company.companyName
                        } else {
                            particular.particularName
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = CircleShape)
                            .background(color = Color.White)
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (company.companyName != "") {
                    company.companyName
                } else {
                    particular.particularName
                },
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (company.description != "") {
                    company.description
                } else {
                    particular.description
                },
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    if (company.companyId != "") {
                        onCompanyContactClick(company)
                    } else if (particular.particularId != "") {
                        onParticularContactClick(particular)
                    }
                },
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