package com.smuniov.telegrambot.handler;

import com.smuniov.telegrambot.annotation.BotCommand;
import com.smuniov.telegrambot.entity.CityData;
import com.smuniov.telegrambot.repository.JpaCityDataRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.stream.Collectors;

@Component
@BotCommand(command = "/citylist")
public class CityListChatBotHandler implements ChatBotHandler {
    private JpaCityDataRepository cityDataRepository;

    public CityListChatBotHandler(JpaCityDataRepository cityDataRepository) {
        this.cityDataRepository = cityDataRepository;
    }

    @Override
    public SendMessage handle(String message, String chatId) {
        String citiesNameList = cityDataRepository.findAll().stream()
                .map(CityData::getName)
                .collect(Collectors.joining("\n"));

        return new SendMessage(chatId, citiesNameList);
    }
}
