package test.auth.testauth.authscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import test.auth.testauth.R
import test.auth.testauth.domain.auth.FieldError
import test.auth.testauth.domain.validation.Failure
import test.auth.testauth.domain.validation.isFailure
import test.auth.testauth.ui.theme.customButtonColors
import test.auth.testauth.ui.theme.customTextFieldColors
import test.auth.testauth.ui.theme.primaryLight
import java.time.Instant
import java.time.ZoneId

@Composable
fun AuthScreen(viewModel: AuthScreenViewModel = koinViewModel()) {

    val state: AuthScreenState by viewModel.authScreenState.collectAsState(Dispatchers.Main.immediate)

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0


    val context = LocalContext.current

    LaunchedEffect(key1 = imeVisible) {
        if (imeVisible) {
            coroutineScope.launch {
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }


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
                )
                .verticalScroll(scrollState)
                .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Bottom + WindowInsetsSides.Top)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.fkit),
                contentDescription = "College Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            FirstLastNameTextField(fullName = state, onValueChanged = viewModel::reduceIntent)

            Spacer(modifier = Modifier.height(24.dp))

            UsernameTextField(username = state, onValueChanged = viewModel::reduceIntent)
            Spacer(modifier = Modifier.height(24.dp))

            BirthdayInput(dateOfBirth = state, onValueChanged = viewModel::reduceIntent)

            Spacer(modifier = Modifier.height(24.dp))

            PasswordTextField(password = state, onValueChanged = viewModel::reduceIntent)

            Spacer(modifier = Modifier.height(24.dp))

            ConfirmPasswordTextField(password = state, onValueChanged = viewModel::reduceIntent)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { Toast.makeText(context, "Goooool", Toast.LENGTH_SHORT).show() },
                enabled = state.isButtonEnabled,
                colors = customButtonColors(),
                shape = ShapeDefaults.Small,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text(text = "Sign Up")
            }

        }

    }
}

@Composable
fun FirstLastNameTextField(
    fullName: AuthScreenState,
    onValueChanged: (AuthIntent) -> Unit,
) {
    val validationState = fullName.fullNameValidationState
    OutlinedTextField(
        value = fullName.fullName,
        onValueChange = {
            onValueChanged(ChangeFullName(it))
        },
        label = { Text(text = "Full Name") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = ShapeDefaults.Small,
        isError = validationState.isFailure,
        supportingText = {
            if (validationState.isFailure) {
                val errorMessage = (validationState as Failure<FieldError>).data.description
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = customTextFieldColors()
    )
}

@Composable
fun UsernameTextField(
    username: AuthScreenState,
    onValueChanged: (AuthIntent) -> Unit
) {
    val validationState = username.usernameValidationState
    OutlinedTextField(
        value = username.username,
        onValueChange = {
            onValueChanged(ChangeUsername(it))
        },
        label = { Text(text = "Username") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = ShapeDefaults.Small,
        isError = validationState.isFailure,
        supportingText = {
            if (validationState.isFailure) {
                val errorMessage = (validationState as Failure<FieldError>).data.description
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = customTextFieldColors()
    )
}

@Composable
fun BirthdayInput(
    dateOfBirth: AuthScreenState,
    onValueChanged: (AuthIntent) -> Unit
) {
    val date = dateOfBirth.dateOfBirth
    val validationState = dateOfBirth.dateOfBirthValidationState
    val isOpen = rememberSaveable { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            readOnly = true,
            value = date,
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { onValueChanged(ChangeDateOfBirth(it)) },
            shape = ShapeDefaults.Small,
            isError = validationState.isFailure,
            supportingText = {
                if (validationState.isFailure) {
                    val errorMessage = (validationState as Failure<FieldError>).data.description
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { isOpen.value = true } // show de dialog
                ) {
                    Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "Calendar")
                }
            },
            colors = customTextFieldColors()
        )
    }

    if (isOpen.value) {
        CustomDatePickerDialog(onAccept = { dateMillis ->
            isOpen.value = false // close dialog

            dateMillis?.let {
                val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                onValueChanged(ChangeDateOfBirth(selectedDate.toString()))
            }
        },
            onCancel = { isOpen.value = false })
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
    password: AuthScreenState,
    onValueChanged: (AuthIntent) -> Unit
) {
    val validationState = password.passwordValidationState
    OutlinedTextField(
        value = password.password,
        onValueChange = {
            onValueChanged(ChangePassword(it))
        },
        label = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = ShapeDefaults.Small,
        isError = validationState.isFailure,
        supportingText = {
            if (validationState.isFailure) {
                val errorMessage = (validationState as Failure<FieldError>).data.description
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            PasswordVisibilityToggle(isPasswordVisible = password.showPassword) {
                onValueChanged(TogglePasswordVisibility)
            }
        },
        visualTransformation = if (password.showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        colors = customTextFieldColors()
    )
}

@Composable
fun ConfirmPasswordTextField(
    password: AuthScreenState,
    onValueChanged: (AuthIntent) -> Unit
) {
    val validationState = password.mismatchValidationState
    OutlinedTextField(
        value = password.confirmPassword,
        onValueChange = {
            onValueChanged(ChangeConfirmPassword(it))
        },
        label = { Text(text = "Confirm Password") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = ShapeDefaults.Small,
        isError = validationState.isFailure,
        supportingText = {
            if (validationState.isFailure) {
                val errorMessage = (validationState as Failure<FieldError>).data.description
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            PasswordVisibilityToggle(isPasswordVisible = password.showConfirmPassword) {
                onValueChanged(ToggleConfirmPasswordVisibility)
            }
        },
        visualTransformation = if (password.showConfirmPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        colors = customTextFieldColors()
    )
}

@Composable
fun PasswordVisibilityToggle(
    isPasswordVisible: Boolean,
    onToggle: () -> Unit
) {
    val image = if (isPasswordVisible) {
        Icons.Filled.Visibility
    } else {
        Icons.Filled.VisibilityOff
    }

    IconButton(onClick = onToggle) {
        Icon(imageVector = image, contentDescription = null)
    }
}



@Preview(showSystemUi = true)
@Composable
fun PreviewAuthScreen() {
    Column {
        AuthScreen()
    }
}