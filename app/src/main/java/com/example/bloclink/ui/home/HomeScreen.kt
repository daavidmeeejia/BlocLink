package com.example.bloclink.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bloclink.model.classes.Company
import com.example.bloclink.ui.BlocLinkHeader
import com.example.bloclink.ui.BlocLinkSlogan
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.SearchTextField
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.utils.featuredCompanies

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Header.
        BlocLinkHeader(navController = navController)

        // Linea divisoria negra.
        Divider(
            color = Color.Black,
            thickness = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Slogan de BlocLink.
        BlocLinkSlogan()

        // Linea divisoria negra.
        Divider(
            color = Color.Black,
            thickness = 0.9.dp,
            modifier = Modifier
                .fillMaxWidth()
        )

        // Resto de la pantalla (Sin padding top para evitar que se corte el contenido antes de llegar al header).
        Box(
            modifier = Modifier
                .fillMaxSize()
                //.padding(start = 40.dp, end = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter), // Alinea la columna en la parte superior del Box.
                horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos dentro de la columna.
            ) {
                // Llamadas a las funciones.
                MainHomeText()

                SearchTextField {  }

                CompaniesCardSection(
                    companies = featuredCompanies, // Usando los datos de prueba que definiste
                    onContactClick = { companyId ->
                        // Lógica para manejar el clic en "Contactar"
                        // Por ejemplo, navegar a otra pantalla:
                        navController.navigate("chat")
                    }
                )

                LogoBlocLink()
                LogoBlocLink()
                LogoBlocLink()
                LogoBlocLink()

            }
        }
    }
}

@Composable
fun MainHomeText() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 40.dp, end = 40.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontFamily = dmsans_regular)) {
                    append("Dinos lo que buscas, nosotros lo encontramos por ti. ")
                }
                withStyle(style = SpanStyle(fontFamily = dmsans_light)) {
                    append("BlocLink te conecta con profesionales ideales para tu proyecto.")
                }
            },
            fontSize = 24.sp,
            lineHeight = 32.sp, // Incrementa el espacio entre líneas
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CompaniesCardSection(companies: List<Company>, onContactClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(companies) { company ->
            CompanyCard(company = company, onContactClick = onContactClick)
        }
    }
}