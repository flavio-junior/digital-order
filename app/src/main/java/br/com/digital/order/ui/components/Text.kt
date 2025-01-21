package br.com.digital.order.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.digital.order.R
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.ui.theme.TypeFont
import br.com.digital.order.ui.theme.Typography

@Composable
fun Title(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    typeFont: TypeFont? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    title: String
) {
    BasicText(
        text = title,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).title(),
        maxLines = maxLines
    )
}

@Composable
fun SubTitle(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    subTitle: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = subTitle,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).subTitle(),
        maxLines = maxLines
    )
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    description: String,
    color: Color = Themes.colors.primary,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = description,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).description(),
        maxLines = maxLines
    )
}

@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    text: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).simpleText(),
        maxLines = maxLines
    )
}

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    text: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).infoText(),
        maxLines = maxLines
    )
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    text: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).smallText(),
        maxLines = maxLines
    )
}

@Composable
fun MiniText(
    modifier: Modifier = Modifier,
    color: Color = Themes.colors.primary,
    text: String,
    typeFont: TypeFont? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = Typography(color = color, textAlign = textAlign, typeFont = typeFont).miniText(),
        maxLines = maxLines
    )
}

@Composable
@Preview
private fun ShowTextPreview() {
    Title(title = stringResource(id = R.string.app_name))
}

val LocalTextStyleDefault = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

@Composable
fun ProvideTextStyleDefault(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle: TextStyle = LocalTextStyleDefault.current.merge(value)
    CompositionLocalProvider(LocalTextStyleDefault provides mergedStyle, content = content)
}
