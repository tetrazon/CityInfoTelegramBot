package com.smuniov.telegrambot.handler;

import com.smuniov.telegrambot.entity.CityData;
import com.smuniov.telegrambot.repository.JpaCityDataRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageChatBotHandler implements ChatBotHandler {
    private JpaCityDataRepository cityDataRepository;

    public MessageChatBotHandler(JpaCityDataRepository cityDataRepository) {
        this.cityDataRepository = cityDataRepository;
    }

    @Override
    public SendMessage handle(String message, String chatId) {
        message = message.substring(0, 1).toUpperCase() + message.substring(1).toLowerCase();
        String cityDescription = cityDataRepository.findByName(message)
                .map(CityData::getDescription)
                .orElse("Извините, нет информации по городу " + message);
        return new SendMessage(chatId, cityDescription);
    }
}
