package br.com.digital.order.networking.resources

import br.com.digital.order.utils.NumbersUtils.NUMBER_ONE_HUNDRED
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

fun translateResponseError(message: String?): String {
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.PORTUGUESE)
        .build()
    val translator = Translation.getClient(options)

    if (!message.isNullOrEmpty() && !isPortuguese(message)) {
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnFailureListener { exception ->
                println("Erro ao baixar o modelo de tradução: ${exception.message}")
            }

        var translatedMessage: String? = null

        translator.translate(message)
            .addOnSuccessListener { result ->
                translatedMessage = result
            }
            .addOnFailureListener { exception ->
                println("Erro na tradução: ${exception.message}")
            }

        while (translatedMessage == null) {
            Thread.sleep(NUMBER_ONE_HUNDRED)
        }

        return translatedMessage ?: message
    } else {
        return message.orEmpty()
    }
}

fun isPortuguese(text: String): Boolean {
    return text.contains("[áàâãéêíóôõúç]".toRegex())
}
