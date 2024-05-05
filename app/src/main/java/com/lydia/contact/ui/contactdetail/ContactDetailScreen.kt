package com.lydia.contact.ui.contactdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lydia.contact.R
import com.lydia.contact.ui.component.ContactImage
import com.lydia.contact.ui.component.DCodeIcon
import com.lydia.contact.ui.component.ImageTextContent
import com.lydia.contact.ui.component.MyIcons
import com.lydia.contact.utils.toReadableDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    navHostController: NavHostController,
    viewModel: ContactDetailViewModel = hiltViewModel()
) {

    val contactDetailUi = viewModel.contact.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.contact_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = MyIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.contact_detail_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ContactDetailContent(
            innerPadding = innerPadding,
            contactDetailUi = contactDetailUi
        )
    }
}

@Composable
fun ContactDetailContent(
    innerPadding: PaddingValues,
    contactDetailUi: State<ContactDetailUi>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        ContactImage(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            imageUrl = contactDetailUi.value.picture
        )

        Spacer(modifier = Modifier.width(20.dp))
        ImageTextContent(
            modifier = Modifier.padding(vertical = 5.dp),
            icon = {
                Icon(
                    imageVector = DCodeIcon.ImageVectorIcon(MyIcons.AccountBox).imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(18.dp)
                )
            },
            text = {
                Text(
                    text = contactDetailUi.value.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        Spacer(modifier = Modifier.width(5.dp))
        ImageTextContent(
            modifier = Modifier.padding(vertical = 5.dp),
            icon = {
                Icon(
                    imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Email).imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(18.dp)
                )
            },
            text = {
                Text(
                    text = contactDetailUi.value.email,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
        Spacer(modifier = Modifier.width(5.dp))
        ImageTextContent(
            modifier = Modifier.padding(vertical = 5.dp),
            icon = {
                Icon(
                    imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Location).imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(18.dp)
                )
            },
            text = {
                Text(
                    text = contactDetailUi.value.address,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        Spacer(modifier = Modifier.width(5.dp))
        ImageTextContent(
            modifier = Modifier.padding(vertical = 5.dp),
            icon = {
                Icon(
                    imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Calendar).imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(18.dp)
                )
            },
            text = {
                Text(
                    text = contactDetailUi.value.dob.toReadableDate(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )

        Spacer(modifier = Modifier.width(5.dp))
        ImageTextContent(
            modifier = Modifier.padding(vertical = 5.dp),
            icon = {
                Icon(
                    imageVector = DCodeIcon.ImageVectorIcon(MyIcons.Phone).imageVector,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(18.dp)
                )
            },
            text = {
                Text(
                    text = contactDetailUi.value.phone,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewContactDetailScreen() {
    val mockContactDetailUi = ContactDetailUi(
        name = "John Doe",
        email = "john.doe@example.com",
        address = "123 Main St, Anytown, USA",
        dob = "1990-01-01T00:00:00Z",
        phone = "+1-123-456-7890",
        picture = "https://randomuser.me/api/portraits/women/27.jpg"
    )

    MaterialTheme {
        ContactDetailContent(
            innerPadding = PaddingValues(),
            contactDetailUi = remember { mutableStateOf(mockContactDetailUi) }
        )
    }
}