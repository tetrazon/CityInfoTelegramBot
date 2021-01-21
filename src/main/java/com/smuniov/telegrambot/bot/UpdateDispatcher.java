package com.smuniov.telegrambot.bot;

import com.smuniov.telegrambot.annotation.BotCommand;
import com.smuniov.telegrambot.handler.ChatBotHandler;
import com.smuniov.telegrambot.handler.MessageChatBotHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Stream;

@Component
public class UpdateDispatcher {
    private final List<ChatBotHandler> handlers;
    private final MessageChatBotHandler messageChatBotHandler;

    public UpdateDispatcher(
            List<ChatBotHandler> handlers,
            MessageChatBotHandler messageChatBotHandler
    ) {
        this.handlers = handlers;
        this.messageChatBotHandler = messageChatBotHandler;
    }

    public SendMessage handle(Update update) {
        String text = null;
        String chatId = null;
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                final Message message = update.getMessage();
                chatId = message.getChatId().toString();
                text = message.getText().split(" ")[0];
                return getHandler(text).handle(text, chatId);
            }
        } catch (UnsupportedOperationException e) {
            return new SendMessage(chatId, "Извините, команды " + text + " нет");
        }
        return null;
    }

    private ChatBotHandler getHandler(String text) {
        if (!text.startsWith("/")) {
            return messageChatBotHandler;
        }
        return handlers.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .filter(h -> Stream.of(h.getClass()
                        .getAnnotation(BotCommand.class)
                        .command())
                        .anyMatch(c -> c.equalsIgnoreCase(text)))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

}
