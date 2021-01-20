package com.smuniov.telegrambot.bot;

import com.smuniov.telegrambot.annotation.BotCommand;
import com.smuniov.telegrambot.entity.CityData;
import com.smuniov.telegrambot.handler.Handler;
import com.smuniov.telegrambot.repository.JpaCityDataRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Stream;

@Component
public class UpdateReceiver {
    private final JpaCityDataRepository cityDataRepository;
    private final List<Handler> handlers;

    public UpdateReceiver(JpaCityDataRepository cityDataRepository, List<Handler> handlers) {
        this.cityDataRepository = cityDataRepository;
        this.handlers = handlers;
    }

    public SendMessage handle(Update update) {
        String text = null;
        String chatId = null;
        try {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final Message message = update.getMessage();
            chatId = message.getChatId().toString();
            text = message.getText().split(" ")[0];
            if (!text.startsWith("/")){
                text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
                String cityDescription = cityDataRepository.findByName(text)
                        .map(CityData::getDescription)
                        .orElse("Извините, нет информации по городу " + text);
                return new SendMessage(chatId, cityDescription);
            } else {
                return getHandler(text).handle(text, chatId);
            }

        }
        } catch (UnsupportedOperationException e) {
            return new SendMessage(chatId, "Извините, команды " + text + " нет");
        }
        return null;
    }

    private Handler getHandler(String text) {
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
