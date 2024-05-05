package com.lydia.contact.ui.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.lydia.contact.R
import com.lydia.contact.network.ConnectivityObserver
import com.lydia.contact.ui.component.InfoBanner
import kotlinx.coroutines.launch

@Composable
fun ContactsScreen(
    onNavigateUp: (Int) -> Unit,
    viewModel: ContactsViewModel = hiltViewModel(),
) {

    val coroutineScope = rememberCoroutineScope()
    val contacts = viewModel.contactsFlow.collectAsLazyPagingItems()

    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle(
        initialValue = ConnectivityObserver.Status.Unavailable
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (contacts.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (networkStatus != ConnectivityObserver.Status.Available) {
            InfoBanner(
                message = stringResource(id = R.string.contact_list_internet_error),
                onRetryClick = {
                    coroutineScope.launch {
                        contacts.retry()
                    }
                },
                shouldShowButton = false
            )
        }

        if (contacts.loadState.append is LoadState.Error || contacts.loadState.refresh is LoadState.Error) {
            InfoBanner(
                message = stringResource(id = R.string.contact_list_failed),
                onRetryClick = {
                    coroutineScope.launch {
                        contacts.retry()
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(contacts.itemCount) { index ->
                contacts[index]?.let { contactUi ->
                    ContactItem(
                        modifier = Modifier,
                        contactUi = contactUi,
                        onClick = onNavigateUp
                    )
                }
            }
            if (contacts.itemCount == 0 && contacts.loadState.refresh is LoadState.Error) {
                item {
                    EmptyContactsContent()
                }
            }
        }
    }
}

@Composable
fun EmptyContactsContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_list_icon),
            contentDescription = stringResource(id = R.string.contact_list_empty),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.contact_list_empty),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}