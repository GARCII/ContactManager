package com.lydia.contact.ui.contacts

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
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
    val context = LocalContext.current
    val contacts = viewModel.contactsFlow.collectAsLazyPagingItems()
    val networkStatus by viewModel.networkStatus.collectAsState()

    LaunchedEffect(key1 = contacts.loadState) {
        if (contacts.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                (contacts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

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
        } else {

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
                horizontalAlignment = Alignment.CenterHorizontally
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
            }
        }
    }
}