package com.spomprt.weightminder.bot;

import com.spomprt.weightminder.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.spomprt.weightminder.bot.Commands.CURRENT;
import static com.spomprt.weightminder.bot.Commands.INITIAL;
import static com.spomprt.weightminder.helper.MessageHelper.isCommand;

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
        String username = message.getFrom().getUserName();
        String text = message.getText();

        if (message.hasText()) {
            if (isCommand(message)) {
                if (text.startsWith("/help")) {
                    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

                    List<KeyboardRow> keyboardRows = new ArrayList<>();

                    KeyboardRow r1 = new KeyboardRow();
                    KeyboardButton b1 = new KeyboardButton();
                    b1.setText(INITIAL);
                    KeyboardButton b2 = new KeyboardButton();
                    b2.setText(CURRENT);
                    r1.add(b1);
                    r1.add(b2);
                    keyboardRows.add(r1);

                    markup.setKeyboard(keyboardRows);

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("Hello epta");
                    sendMessage.setReplyMarkup(markup);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                }
                if (text.startsWith(INITIAL)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());

                    sendMessage.setText(personService.get(username).getInitial());

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                } else if (text.startsWith(CURRENT)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());

                    sendMessage.setText(personService.get(username).getCurrent());

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                }
            } else {
                personService.register(username);

                personService.addRecord(username, Double.valueOf(text));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "WeightMinder";
    }

}
