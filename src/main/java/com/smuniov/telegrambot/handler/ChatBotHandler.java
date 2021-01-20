package com.smuniov.telegrambot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public interface ChatBotHandler {
    SendMessage handle(String message, String chatId);
}
