package com.smuniov.telegrambot.handler;

import com.smuniov.telegrambot.annotation.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@BotCommand(command = {"/help", "/start"})
public class HelpChatBotHandler implements ChatBotHandler {
    private String helpString = "Привет, это справочник по городам." +
            "\nОтправь /help чтобы увидеть это сообщение снова." +
            "\nЧтобы увидеть справку по городу, набери название города." +
            "\nСписок доступных команд:\n";
    private final List<ChatBotHandler> handlerList;

    public HelpChatBotHandler(List<ChatBotHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public SendMessage handle(String message, String chatId) {
        StringBuilder builder = new StringBuilder(helpString);
        handlerList.add(this);
        String commandsString = handlerList.stream()
                .filter(h -> h.getClass()
                        .isAnnotationPresent(BotCommand.class))
                .flatMap(h -> Stream.of(h
                        .getClass()
                        .getAnnotation(BotCommand.class)
                        .command()))
                .collect(Collectors.joining("\n"));
        builder.append(commandsString);
        return new SendMessage(chatId, builder.toString());
    }
}
