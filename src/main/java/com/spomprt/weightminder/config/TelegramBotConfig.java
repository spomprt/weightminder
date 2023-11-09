package com.spomprt.weightminder.config;

import com.spomprt.weightminder.bot.WeightMinderBot;
import com.spomprt.weightminder.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public WeightMinderBot weightMinderBot(
            PersonService personService
    ) {
        return new WeightMinderBot(token, personService);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(
            WeightMinderBot weightMinderBot
    ) {
        TelegramBotsApi telegramBotsApi;

        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(weightMinderBot);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
