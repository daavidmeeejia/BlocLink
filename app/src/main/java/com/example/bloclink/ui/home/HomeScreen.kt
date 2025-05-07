package com.example.bloclink.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bloclink.R
import com.example.bloclink.model.classes.Company
import com.example.bloclink.model.classes.Particular
import com.example.bloclink.model.classes.User
import com.example.bloclink.model.db.ParticularViewModel
import com.example.bloclink.model.db.UserDataViewModel
import com.example.bloclink.model.db.UserState
import com.example.bloclink.ui.BlocLinkHeader
import com.example.bloclink.ui.BlocLinkSlogan
import com.example.bloclink.ui.SearchTextField
import com.example.bloclink.ui.login.dmsans_light
import com.example.bloclink.ui.login.dmsans_regular
import com.example.bloclink.ui.simpleHorizontalScrollbar
import com.example.bloclink.utils.featuredCompanies
import com.example.bloclink.utils.featuredParticulars
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewmodel: ParticularViewModel = ParticularViewModel(context = LocalContext.current),
    userViewModel: UserDataViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerStateProfile = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    userViewModel.getUser()
    val currentUser = userViewModel.user.value
    val show = rememberSaveable { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Menú", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = dmsans_regular
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, top = 20.dp),
                        thickness = 0.9.dp,
                        color = Color.Black
                    )

                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* Acción para Opción 1 */ }) {
                        Text("Galería", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                    }
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* Acción para Opción 2 */ }) {
                        Text("Cámara", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                    }
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* Acción para Opción 3 */ }) {
                        Text("ChatBot", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                    }
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* Acción para Opción 2 */ }) {
                        Text("Ayuda", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                    }
                    Spacer(modifier = Modifier.fillMaxHeight(0.78f))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        thickness = 0.9.dp,
                        color = Color.Black
                    )
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { uriHandler.openUri("https://play.google.com/store/apps/") }) {
                        Text("Valora nuestra aplicación", fontSize = 20.sp, fontFamily = dmsans_light)
                    }
                }
            }
        }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerStateProfile,
                gesturesEnabled = true,
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    modifier = Modifier.size(40.dp), onClick = {
                                        show.value = true
                                    }, content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.edit_user),
                                            modifier = Modifier
                                                .fillMaxSize(0.5f),
                                            contentDescription = "edit",
                                            tint = Color.Gray,
                                        )
                                    })
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AvatarImage(
                                    viewModel = userViewModel,
                                    currentuser = currentUser,
                                    show = show,
                                    navController = navController,
                                    profiledrawerState = drawerStateProfile,
                                    size = 175.dp
                                )
                            }


                            Text(
                                currentUser.name + " " + currentUser.surname,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                currentUser.email, fontSize = 14.sp, fontWeight = FontWeight.Light
                            )

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                thickness = 0.9.dp,
                                color = Color.Black
                            )

                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Acción para Opción 1 */ }) {
                                Text("Facturas", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                            }
                            TextButton(onClick = { /* Acción para Opción 2 */ }) {
                                Text("Métodos de pago", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                            }
                            TextButton(onClick = { /* Acción para Opción 3 */ }) {
                                Text("Proyectos", fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                            }
                            Spacer(modifier = Modifier.fillMaxHeight(0.67f))
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                thickness = 0.9.dp,
                                color = Color.Black
                            )
                            TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
                                navController.navigate("login")
                                FirebaseAuth.getInstance().signOut()
                            }) {
                                Text("Cerrar Sesión", color = Color.Red, fontSize = 20.sp, fontFamily = dmsans_light, modifier = Modifier.padding(vertical = 20.dp))
                            }
                        }
                    }
                }
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Header.
                        BlocLinkHeader(
                            navController = navController,
                            drawerState = drawerState,
                            profiledrawerState = drawerStateProfile,
                            scope = scope,
                            userViewModel = userViewModel,
                            currentUser = currentUser,
                            show = show,
                            drawerStateProfile = drawerStateProfile
                        )

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

                                /*//Mostrar los particulares desde el json
                                when (val result = viewModel.response.value) {
                                    is DataState.Loading -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                //CircularProgressIndicator()
                                                Spacer(modifier = Modifier.height(16.dp))
                                                LinearProgressIndicator()
                                            }
                                        }
                                    }

                                    is DataState.Success -> {
                                        SecondCardSection(
                                            type = "particular",
                                            particulars = result.data,
                                            onContactClick = { particular ->
                                                navController.navigate("chat/{${particular.serialize()}}")
                                            }
                                        )
                                    }

                                    is DataState.Failure -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text(text = result.message)
                                        }
                                    }

                                    is DataState.Empty -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text(text = "No se han encontrado resultados")
                                        }
                                    }*/

                                Divider(
                                    color = Color.Black,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 90.dp,
                                            bottom = 90.dp
                                        ) //29.9 porque 30 se ve gris.
                                )
                            }
                        }
                    }
                }
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
                    CompanyCard(company = company, onCompanyContactClick = onContactClick)
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


@Composable
fun ProfileSelector(
    show: MutableState<Boolean>,
    currentuser: User,
    navcontroller: NavController,
    scope: CoroutineScope,
    profiledrawerState: DrawerState
) {
    Dialog(onDismissRequest = { show.value = false }) {
        val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
        val context = LocalContext.current
        val bitmapImages = mutableListOf<Bitmap?>()
        val avatarImages = mutableListOf<String>()
        for (i in LocalContext.current.assets.list("avatars")!!) {
            Log.d("avatars", "Loading avatar -> avatars/$i")
            if (i != null) {
                bitmapState.value =
                    BitmapFactory.decodeStream(context.assets.open("avatars/$i"))
                bitmapImages.add(bitmapState.value)
                avatarImages.add("avatars/$i")
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = ":Selecciona un avatar",
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    columns = GridCells.Fixed(4),
                    content = {
                        items(avatarImages) { image ->
                            if (bitmapState.value != null) {
                                val bitmap =
                                    bitmapImages[avatarImages.indexOf(image)]!!.asImageBitmap()
                                Image(
                                    //painter = rememberAsyncImagePainter(model = image),
                                    bitmap = bitmap,
                                    contentDescription = "",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            Log.d("avatar", "New profile -> $image")
                                            show.value = false
                                            scope.launch { profiledrawerState.close() }
                                            changeAvatar(image, currentuser)
                                            navcontroller.navigate("home")
                                            scope.launch { profiledrawerState.open() }
                                        })
                            }
                        }
                    })
            }
        }
    }
}


@Composable
fun AvatarImage(
    viewModel: UserDataViewModel,
    currentuser: User,
    navController: NavController,
    show: MutableState<Boolean>,
    profiledrawerState: DrawerState,
    size: Dp
) {
    val path = "avatars/"

    val context = LocalContext.current
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    Log.d("avatar", currentuser.avatar)
    when (val result = viewModel.state.value) {
        is UserState.Loading -> {
            Log.d("avatar", "Loading")
            //CircularProgressIndicator()
        }

        is UserState.Success -> {
            LaunchedEffect(Unit) {
                val avatar = context.assets.open(currentuser.avatar)
                bitmapState.value = BitmapFactory.decodeStream(avatar)
            }
        }

        is UserState.Empty -> {
            LaunchedEffect(Unit) {
                val avatar = context.assets.open("${path}profile.png")
                bitmapState.value = BitmapFactory.decodeStream(avatar)
            }

        }

        is UserState.Failure -> {
            LaunchedEffect(Unit) {
                val avatar = context.assets.open("${path}profile.png")
                bitmapState.value = BitmapFactory.decodeStream(avatar)
            }
        }
    }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .size(size + 25.dp),
        contentAlignment = Alignment.Center
    ) {
        if (bitmapState.value != null) {
            val bitmap = bitmapState.value!!.asImageBitmap()
            Image(
                bitmap = bitmap, contentDescription = "Avatar",
                modifier = Modifier.size(size),
                contentScale = ContentScale.Crop
            )
        }
    }
    if (show.value) {
        ProfileSelector(
//avatarImages = avatarImages,
            show = show,
            currentuser = currentuser,
            navcontroller = navController,
            scope = rememberCoroutineScope(),
            profiledrawerState = profiledrawerState,
        )
    }
}

fun changeAvatar(image: String, currentUser: User) {
    Log.d("avatar_update", "Updating ${currentUser.userId}")
    FirebaseFirestore.getInstance()
        .collection("users")
        .whereEqualTo("userId", currentUser.userId)
        .get()
        .addOnSuccessListener {
            for (doc in it.documents) {
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(doc.id)
                    .update("avatar", image)
                    .addOnSuccessListener {
                        Log.d("avatar_update", "Avatar successfully updated!")
                    }.addOnFailureListener { e ->
                        Log.d("avatar_update", "Error updating avatar $e")
                    }
            }
        }
}
