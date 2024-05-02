package com.lydia.contact.ui.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lydia.contact.ui.component.ContactImage
import com.lydia.contact.ui.component.DCodeIcon
import com.lydia.contact.ui.component.ImageTextContent
import com.lydia.contact.ui.component.MyIcons

@Composable
fun ContactItem(
    modifier: Modifier,
    contactUi: ContactUi,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onClick(contactUi.id)
                }
        ) {
            ContactImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                imageUrl = contactUi.imageUrl,
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = contactUi.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                ImageTextContent(
                    modifier = Modifier.padding(vertical = 5.dp),
                    icon = {
                        Icon(
                            imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Phone).imageVector,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(15.dp)
                        )
                    },
                    text = {
                        Text(
                            text = contactUi.phone,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                )

                ImageTextContent(
                    icon = {
                        Icon(
                            imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Email).imageVector,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(15.dp)
                        )
                    },
                    text = {
                        Text(
                            text = contactUi.email,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.background(Color.Transparent)) {
        ContactItem(
            modifier = Modifier,
            contactUi = ContactUi(
                id = 1,
                name = "Amine",
                phone = "+33077662698",
                imageUrl = "url",
                email = "aminegarci100@gmail.com"
            ), onClick = {}
        )
    }
}