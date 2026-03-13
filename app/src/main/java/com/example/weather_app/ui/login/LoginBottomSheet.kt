package com.example.weather_app.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.util.CustomFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginBottomSheet(
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        // when typing the screen will be 50%
        // the keyboard will pop up and cover your input fields or the button.
        skipPartiallyExpanded = true,
        confirmValueChange = { true }  // ensure no state blocking
    )

    // if want full screen bottom sheet please set up fillMaxHeight in ModalBottomSheet or Column inside the sheet
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = WeatherTheme.colors.background,
        dragHandle = {
            // Custom drag bar
            Box(
                Modifier
                    .padding(vertical = 12.dp)
                    .width(48.dp)
                    .height(4.dp)
                    .background(Color.LightGray, CircleShape)
            )
        }
    ) {
        LoginContent(onDismiss)
    }
}

@Composable
private fun LoginContent(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 40.dp)
            .background(WeatherTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(onDismiss)

        Text(
            text = stringResource(R.string.welcome_back),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = WeatherTheme.colors.textPrimary
        )

        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
            text = stringResource(R.string.sign_in_content),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 16.sp,
                lineHeight = 16.sp,
            ),
            color = WeatherTheme.colors.textSecondary
        )

        GoogleLoginSection()

        Spacer(modifier = Modifier.height(20.dp))

        DividerSection()

        Spacer(modifier = Modifier.height(20.dp))

        EmailInputSection(
            valueProvider = { email },
            onValueChange = { email = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInputSection(
            valueProvider = { password },
            onValueChange = { password = it }
        )

        SignInSection()

        SignUpSection()
    }
}
@Composable
private fun HeaderSection(onDismiss: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
        }
    }
}

@Composable
private fun GoogleLoginSection() {
    Button(
        onClick = { /* TODO: Trigger Google Auth */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = WeatherTheme.colors.googleButtonColor                    )
    ) {
        Text(
            text = stringResource(R.string.continue_w_google),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 16.sp,
                lineHeight = 16.sp,
            ),
            color = WeatherTheme.colors.onGoogleButtonColor,
        )
    }
}

@Composable
private fun DividerSection() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = WeatherTheme.colors.textSecondary
        )
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(R.string.or_continue_w_email),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Medium
            ),
            color = WeatherTheme.colors.textSecondary
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = WeatherTheme.colors.textSecondary
        )
    }
}

@Composable
private fun EmailInputSection(valueProvider: () -> String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.email),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Medium
            ),
        )

        OutlinedTextField(
            value = valueProvider(),
            onValueChange = { onValueChange(it) },
            placeholder = { Text(stringResource(R.string.email_hint)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun PasswordInputSection(valueProvider: () -> String, onValueChange: (String) -> Unit) {
    Column {
        // Password Input
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.password),
                style = TextStyle(
                    fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            TextButton(onClick = { /* Forgot */ }) {
                Text(
                    text = stringResource(R.string.forgot),
                    color = WeatherTheme.colors.brandColor
                )
            }
        }

        OutlinedTextField(
            value = valueProvider(),
            onValueChange = { onValueChange(it) },
            placeholder = { Text(stringResource(R.string.password_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun SignInSection() {
    Button(
        onClick = { /* Handle Login */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = WeatherTheme.colors.brandColor)
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            color = WeatherTheme.colors.onBrandColor
        )
    }
}

@Composable
private fun SignUpSection() {
    Row(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(R.string.dont_have_account),
            color = WeatherTheme.colors.textSecondary
        )
        Text(
            text = stringResource(R.string.sign_up),
            style = TextStyle(
                fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            color = WeatherTheme.colors.brandColor,
            modifier = Modifier.clickable { /* Nav to Register */ }
        )
    }
}

@Preview
@Composable
private fun preview() {
    LoginBottomSheet(
        onDismiss = {}
    )
}