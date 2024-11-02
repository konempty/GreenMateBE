package kr.kro.jayden_bin.greenmate.controller.chatbot.request

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.Locale

data class ChatRequest(
    val text: String,
    val sender: SenderType,
)

enum class SenderType {
    USER,
    BOT,
    ;

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(value: String): SenderType =
            value.uppercase(Locale.KOREAN).let { upperValue -> entries.first { it.name == upperValue } }
    }
}
