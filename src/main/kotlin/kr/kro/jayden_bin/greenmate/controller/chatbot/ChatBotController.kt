package kr.kro.jayden_bin.greenmate.controller.chatbot

import kr.kro.jayden_bin.greenmate.controller.chatbot.request.ChatRequest
import kr.kro.jayden_bin.greenmate.controller.chatbot.response.ChatResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/chatbots")
class ChatBotController {
    @PostMapping
    fun chat(
        @RequestBody
        request: List<ChatRequest>,
    ): ChatResponse {
        TODO()
    }
}
