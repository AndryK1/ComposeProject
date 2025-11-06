package com.practicum.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.practicum.composeproject.data.Contact
import com.practicum.composeproject.ui.theme.ComposeProjectTheme

class MainActivity : ComponentActivity() {

    private val imageUrl = "https://cdn.stocksnap.io/img-thumbs/960w/clocks-timezones_VTDRJZWTT3.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contact = Contact(
            name = "Евгений",
            surname = "Андреевич",
            familyName = "Лукашин",
            //designer такие фото не показывает
            imageRes = imageUrl,
            isFavorite = true,
            phone = "7 905 659 87 05",
            address = "г. Москва, 3-я улица строителей, дом 25, кв. 25",
            email = "s.dsdkjaj@gmail.com"
        )

        setContent {
            Box(
                modifier = Modifier.padding(36.dp)
            ){
                ContactDetails(contact)
            }
        }
    }
}


@Composable
fun ContactDetails(contact: Contact){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BuildInitials(
            imageUrl = contact.imageRes,
            name = contact.name,
            surname = contact.surname,
            familyName = contact.familyName,
            isFavorite = contact.isFavorite
        )
        BuildAdditionalInfo(
            phone = contact.phone,
            address = contact.address,
            email = contact.email
        )
    }


}

@Composable
fun BuildInitials(imageUrl: String?,name: String, surname: String?,familyName: String , isFavorite : Boolean){
    imageUrl?.let {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.sizeIn(minWidth = 24.dp, minHeight = 24.dp, maxHeight = 120.dp, maxWidth = 120.dp),
                model = imageUrl,
                contentDescription = null
            )
            SetInitialsText(name,surname,familyName,isFavorite)
        }
    } ?: run {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.wrapContentSize(),
                    painter = painterResource(R.drawable.circle),
                    contentDescription = null,
                    tint = Color.LightGray
                )
                Text((name.take(1) + familyName.take(1)))
            }
            SetInitialsText(name,surname,familyName,isFavorite)
        }
    }
}

@Composable
fun SetInitialsText(name : String, surname: String?, familyName: String, isFavorite : Boolean){
    Text(text = "$name ${surname ?: ""}",
        modifier = Modifier.padding(top = 8.dp))
    Row(){
        Text(
            text = familyName,
            modifier = Modifier.padding(top = 4.dp)
        )
        if (isFavorite) {
            Image(
                modifier = Modifier.wrapContentSize(),
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null
            )
        }
    }
}

@Composable
fun BuildAdditionalInfo(phone: String?, address: String, email: String?){
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 34.dp, end = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        phone?.let{
            InfoRow("${stringResource(R.string.phone)}: +", phone)
        } ?: run{
            InfoRow("${stringResource(R.string.phone)}: ", "—")
        }
        InfoRow("${stringResource(R.string.address)}: ", address, 180.dp)
        InfoRow("${stringResource(R.string.email)}: ", email)
    }
}

@Composable
fun InfoRow(label: String, value: String?, valueWidth: Dp? = null){
    Row(
        modifier = Modifier.padding(bottom = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        if(value != null){
            Text(
                text = label,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = value,
                modifier = if (valueWidth != null) {
                    Modifier.width(valueWidth)
                } else Modifier,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ContactsPreviewOne(){
    val contact = Contact(
        name = "Евгений",
        surname = "Андреевич",
        familyName = "Лукашин",
        //designer такие фото не показывает
        imageRes = null,
        isFavorite = true,
        phone = "7 905 659 87 05",
        address = "г. Москва, 3-я улица строителей, дом 25, кв. 25",
        email = "s.dsdkjaj@gmail.com"

    )
    ContactDetails(contact)
}

@Preview(showSystemUi = true)
@Composable
fun ContactsPreviewSecond(){
    val contact = Contact(
        name = "Василий",
        surname = null,
        familyName = "Васильев",
        imageRes = "https://cdn.stocksnap.io/img-thumbs/960w/clocks-timezones_VTDRJZWTT3.jpg",
        phone = null,
        address = "г. Москва, 3-я улица строителей, дом 25, кв. 25",
        email = null

    )
    ContactDetails(contact)
}

@Preview(showSystemUi = true)
@Composable
fun BuildAdditionalInfoPreview(){
    BuildAdditionalInfo(
        "7 905 659 87 05",
        "г. Москва, 3-я улица строителей, дом 25, кв. 25",
        "s.dsdkjaj@gmail.com",

    )
}

@Preview(showSystemUi = true)
@Composable
fun BuildInitialsPreview(){
    BuildInitials(
        "https://cdn.stocksnap.io/img-thumbs/960w/clocks-timezones_VTDRJZWTT3.jpg",
        "Alex",
        "LExov",
        "FamilyName? who is it?",
        true
    )
}


