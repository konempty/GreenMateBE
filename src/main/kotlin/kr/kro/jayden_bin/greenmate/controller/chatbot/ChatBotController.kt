package kr.kro.jayden_bin.greenmate.controller.chatbot

import kr.kro.jayden_bin.greenmate.controller.chatbot.request.ChatRequest
import kr.kro.jayden_bin.greenmate.controller.chatbot.response.ChatResponse
import kr.kro.jayden_bin.greenmate.entity.user.User
import kr.kro.jayden_bin.greenmate.service.ChatBotService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/chatbots")
class ChatBotController(
    private val chatBotService: ChatBotService,
) {
    @PostMapping
    fun chat(
        user: User,
        @RequestBody
        request: List<ChatRequest>,
    ): ChatResponse = chatBotService.chat(user, request)
}
