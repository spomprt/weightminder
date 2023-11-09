package com.spomprt.weightminder.notification;

import com.spomprt.weightminder.bot.WeightMinderBot;
import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class StepOnTheScaleReminder {

    private final WeightMinderBot weightMinderBot;
    private final PersonService personService;

    @Async
//    @Scheduled(cron = "0 6 * * *")
    @Scheduled(cron = "* * * * *")
    public void remind() {
        try (Stream<Person> persons = personService.getAll()) {
            persons.forEach(
                    p -> {
                        SendMessage message = new SendMessage();
                        message.setChatId(p.getChat());
                        message.setText("Не забудь встать на весы!");
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
