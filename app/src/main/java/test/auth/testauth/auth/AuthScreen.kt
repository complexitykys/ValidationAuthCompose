package test.auth.testauth.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.auth.testauth.R
import test.auth.testauth.ui.theme.onPrimaryContainerLight
import test.auth.testauth.ui.theme.primaryLight
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AuthScreen() {

    var fullName by rememberSaveable {
        mutableStateOf("")
    }

    var username by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordConfirmed by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight)
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth()
                .background(
                    Color.White, RoundedCornerShape(topEnd = 150.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    WindowInsets.systemBars
                        .add(WindowInsets(left = 16.dp, right = 16.dp))
                        .asPaddingValues()
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.fkit),
                contentDescription = "College Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            FirstLastNameTextField(fullName = fullName, onValueChanged = { fullName = it })

            Spacer(modifier = Modifier.height(24.dp))

            UsernameTextField(username = username, onValueChanged = { username = it })
            Spacer(modifier = Modifier.height(24.dp))

            BirthdayInput()

            Spacer(modifier = Modifier.height(24.dp))

            PasswordTextField(password = password, onValueChanged = { password = it })

            Spacer(modifier = Modifier.height(24.dp))

            ConfirmPasswordTextField(password = passwordConfirmed,
                onValueChanged = { passwordConfirmed = it })

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show() },
                colors = ButtonDefaults.buttonColors(primaryLight),
                shape = ShapeDefaults.Small,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text(text = "Sign Up", color = Color.White)
            }

        }

    }
}

@Composable
fun FirstLastNameTextField(
    fullName: String, onValueChanged: (String) -> Unit
) {
    MyOutlinedTextField(
        value = fullName,
        onValueChanged = onValueChanged,
        label = "Full Name"
    )
}

@Composable
fun UsernameTextField(
    username: String, onValueChanged: (String) -> Unit
) {
    MyOutlinedTextField(
        value = username,
        onValueChanged = onValueChanged,
        label = "Username"
    )
}

@Composable
fun BirthdayInput() {
    val date = rememberSaveable { mutableStateOf<LocalDate?>(null) }
    val isOpen = rememberSaveable { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            readOnly = true,
            value = date.value?.format(DateTimeFormatter.ISO_DATE) ?: "",
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            shape = ShapeDefaults.Small,
            trailingIcon = {
                IconButton(onClick = { isOpen.value = true } // show de dialog
                ) {
                    Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "Calendar")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryLight,
                unfocusedBorderColor = onPrimaryContainerLight,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                unfocusedLabelColor = primaryLight,
                focusedLabelColor = onPrimaryContainerLight,
                disabledLabelColor = onPrimaryContainerLight,
                cursorColor = Color.Black
            )
        )
    }

    if (isOpen.value) {
        CustomDatePickerDialog(onAccept = {
            isOpen.value = false // close dialog

            if (it != null) { // Set the date
                date.value = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
            }
        }, onCancel = {
            isOpen.value = false //close dialog
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit, onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = {}, confirmButton = {
        Button(onClick = { onAccept(state.selectedDateMillis) }) {
            Text("Accept")
        }
    }, dismissButton = {
        Button(onClick = onCancel) {
            Text("Cancel")
        }
    }) {
        DatePicker(state = state)
    }
}

@Composable
fun PasswordTextField(
    password: String, onValueChanged: (String) -> Unit
) {
    MyOutlinedTextField(
        value = password,
        onValueChanged = onValueChanged,
        label = "Password"
    )
}

@Composable
fun ConfirmPasswordTextField(
    password: String, onValueChanged: (String) -> Unit
) {
    MyOutlinedTextField(
        value = password,
        onValueChanged = onValueChanged,
        label = "Confirm Password"
    )
}

private fun parseFullName(fullName: String): Pair<String, String> {
    val parts = fullName.split(" ")
    return if (parts.size >= 2) {
        Pair(parts[0], parts[1])
    } else {
        Pair(fullName, "")
    }
}

@Composable
fun MyOutlinedTextField(
    value: String, onValueChanged: (String) -> Unit, label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = ShapeDefaults.Small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryLight,
            unfocusedBorderColor = onPrimaryContainerLight,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.Black,
            unfocusedLabelColor = primaryLight,
            focusedLabelColor = onPrimaryContainerLight,
            disabledLabelColor = onPrimaryContainerLight,
            cursorColor = Color.Black
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewAuthScreen() {
    Column {
        AuthScreen()
    }
}