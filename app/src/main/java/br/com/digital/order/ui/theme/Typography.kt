package br.com.digital.order.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import br.com.digital.order.R

private val fontSize = FontSize

data class Typography(
    val color: Color = Color.Black,
    val textAlign: TextAlign = TextAlign.Start,
    val typeFont: TypeFont? = null
) {

    @Composable
    fun title() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize32,
        fontWeight = FontWeight.SemiBold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun subTitle() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize24,
        fontWeight = FontWeight.Medium,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_MEDIUM),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun description() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize20,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun simpleText(): TextStyle = TextStyle(
        color = color,
        fontSize = FontSize.fontSize16,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun infoText() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize14,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun smallText() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize12,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun miniText() = TextStyle(
        color = color,
        fontSize = FontSize.fontSize8,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )
}

val LocalTypography = staticCompositionLocalOf {
    Typography()
}

@Composable
private fun SelectFontFamily(typeFont: TypeFont): FontFamily {
    return when (typeFont) {
        TypeFont.ONEST_BOLD -> FontFamily(Font(R.font.onest_bold))
        TypeFont.ONEST_MEDIUM -> FontFamily(Font(R.font.onest_medium))
        TypeFont.ONEST_REGULAR -> FontFamily(Font(R.font.onest_regular))
    }
}
