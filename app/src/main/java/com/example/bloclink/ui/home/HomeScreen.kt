package com.example.bloclink.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
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
import com.example.bloclink.model.classes.Particular
import com.example.bloclink.ui.BlocLinkHeader
import com.example.bloclink.ui.BlocLinkSlogan
import com.example.bloclink.ui.LogoBlocLink
import com.example.bloclink.ui.SearchTextField
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.simpleHorizontalScrollbar
import com.example.bloclink.utils.featuredCompanies
import com.example.bloclink.utils.featuredParticulars

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

                SearchTextField { }

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp) //29.9 porque 30 se ve gris.
                )


                FirstCardSection(
                    type = "company",
                    companies = featuredCompanies,
                    onContactClick = { company ->
                        navController.navigate("chat/{${company.serialize()}}")
                    }
                )

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 90.dp) //29.9 porque 30 se ve gris.
                )
                SecondCardSection(
                    type = "particular",
                    particulars = featuredParticulars,
                    onContactClick = { particular ->
                        navController.navigate("chat/{${particular.serialize()}}")
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
            .padding(top = 35.dp, start = 40.dp, end = 40.dp)
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
fun SectionTitle(type: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (type == "company") {
                "Empresas destacadas"
            } else if (type == "particular") {
                "Particulares destacados"
            } else {
                ""
            },
            fontSize = 24.sp,
            fontFamily = dmsans_regular,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun FirstCardSection(
    type: String,
    companies: List<Company>,
    onContactClick: (Company) -> Unit
) {
    val state = rememberLazyListState()
    val item = remember { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()

    if (type == "company") {
        SectionTitle(type)
    } else if (type == "particular") {
        SectionTitle(type)
    }


    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .collect { it ->
                if (it != item.value && it >= 0) {
                    item.value = it
                }
            }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Column {
            LazyRow(
                state = state,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .simpleHorizontalScrollbar(state),
                horizontalArrangement = Arrangement.spacedBy(16.dp),

                ) {
                items(companies) { company ->
                    item.intValue = companies.indexOf(company)
                    CompanyCard(company = company, onCompanyContactClick =  onContactClick)
                }
            }

            // Botones flecha izquierda y derecha.
            /*Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 110.dp)
            ) {
                Button(onClick = {
                     if (item.intValue > 0) {
                     item.intValue--
                    coroutine.launch {
                        state.animateScrollToItem(item.intValue)
                         }
                    }

                }
                ) {
                    Text(text = "<")
                }
                Button(onClick = {
                    if (item.intValue < companies.size -1) {
                    item.intValue++
                    coroutine.launch {
                        state.animateScrollToItem(item.intValue)
                        }
                    }
                }) {
                    Text(text = ">")
                }
            }*/
        }
    }
}

@Composable
fun SecondCardSection(
    type: String,
    particulars: List<Particular>,
    onContactClick: (Particular) -> Unit
) {
    val state = rememberLazyListState()
    val item = remember { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()

    if (type == "particular") {
        SectionTitle(type)
    } else if (type == "particular") {
        SectionTitle(type)
    }


    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .collect { it ->
                if (it != item.value && it >= 0) {
                    item.value = it
                }
            }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Column {
            LazyRow(
                state = state,
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .simpleHorizontalScrollbar(state),
                horizontalArrangement = Arrangement.spacedBy(16.dp),

                ) {
                items(particulars) { particular ->
                    item.intValue = particulars.indexOf(particular)
                    CompanyCard(particular = particular, onParticularContactClick = onContactClick)
                }
            }
        }
    }
}