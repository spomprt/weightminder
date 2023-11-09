package com.spomprt.weightminder.bot;

import com.spomprt.weightminder.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
public class WeightMinderBot extends TelegramLongPollingBot {

    private final PersonService personService;

    public WeightMinderBot(String botToken,
                           PersonService personService) {
        super(botToken);
        this.personService = personService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        User from = message.getFrom();
        String text = message.getText();

        personService.register(from.getUserName());

        personService.addRecord(from.getUserName(), Double.valueOf(text));
    }

    @Override
    public String getBotUsername() {
        return "WeightMinder";
    }

}
