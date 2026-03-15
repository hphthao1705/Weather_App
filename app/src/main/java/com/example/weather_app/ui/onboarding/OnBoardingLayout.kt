package com.example.weather_app.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.ui.WeatherTheme
import com.example.weather_app.util.CustomFontFamily

@Composable
internal fun OnboardingScreen(
    onButtonClick: () -> Unit,
    onLogInClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WeatherTheme.colors.brandColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            painter = painterResource(id = R.drawable.on_boarding_img),
            contentDescription = "World image"
        )

        IntroductoryComposable(
            onButtonClick = { onButtonClick() },
            onLogInClick = { onLogInClick() }
        )
    }
}

@Composable
private fun IntroductoryComposable(onButtonClick: () -> Unit, onLogInClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IntroductoryContent()

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 43.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(WeatherTheme.colors.brandColor)
                    .clickable {
                        onButtonClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 21.dp),
                    text = stringResource(R.string.get_started_button),
                    style = TextStyle(
                        fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LogInComposable(onClick = { onLogInClick() })

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun IntroductoryContent() {
    Spacer(modifier = Modifier.height(45.dp))

    Text(
        modifier = Modifier.padding(horizontal = 22.dp),
        text = stringResource(R.string.onboarding_title),
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 20.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        ),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        modifier = Modifier.padding(horizontal = 19.dp),
        text = stringResource(R.string.onboarding_description),
        style = TextStyle(
            color = WeatherTheme.colors.textSecondary,
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center
        ),
    )
}

@Composable
private fun LogInComposable(onClick: () -> Unit) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append("${stringResource(R.string.already_have_an_account)} ")
        }
        withLink(LinkAnnotation.Clickable(
            tag = "login",
            linkInteractionListener = { onClick() }
        )) {
            withStyle(style = SpanStyle(color = WeatherTheme.colors.brandColor)) {
                append(stringResource(R.string.log_in))
            }
        }
    }

    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 19.dp),
        style = TextStyle(
            fontFamily = CustomFontFamily.SF_PRO_DISPLAY_TEXT,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center
        )
    )
}

@Preview
@Composable
private fun Preview() {
//    OnboardingScreen(onButtonClick = {}, onLogInClick = {})
}