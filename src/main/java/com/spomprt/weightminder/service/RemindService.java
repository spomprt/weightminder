package com.spomprt.weightminder.service;

import com.spomprt.weightminder.bot.WeightMinderBot;
import com.spomprt.weightminder.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RemindService {

    private final PersonService personService;
    private final WeightMinderBot weightMinderBot;

    @Async
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void remind() {
        try (Stream<Person> persons = personService.getAll()) {
            persons.forEach(
                p -> {
                    SendMessage message = new SendMessage();
                    message.setChatId(p.getChat());
                    message.setText("Доброе утро. Не забудь встать на весы!");
                    try {
                        weightMinderBot.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        }
    }
}
