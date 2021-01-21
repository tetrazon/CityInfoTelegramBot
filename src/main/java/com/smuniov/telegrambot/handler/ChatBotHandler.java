package com.smuniov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ChatBotHandler {
    SendMessage handle(String message, String chatId);
}
