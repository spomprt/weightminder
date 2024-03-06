package com.spomprt.weightminder.bot;

import com.spomprt.weightminder.service.ChartService;
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

import static com.spomprt.weightminder.bot.Commands.CHART;
import static com.spomprt.weightminder.bot.Commands.CURRENT;
import static com.spomprt.weightminder.bot.Commands.HELP;
import static com.spomprt.weightminder.bot.Commands.INITIAL;
import static com.spomprt.weightminder.bot.Commands.LAST_10;
import static com.spomprt.weightminder.bot.Commands.START;
import static com.spomprt.weightminder.helper.MessageHelper.isCommand;

@Slf4j
public class WeightMinderBot extends TelegramLongPollingBot {

    private final PersonService personService;
    private final ChartService chartService;

    public WeightMinderBot(String botToken,
                           PersonService personService,
                           ChartService chartService) {
        super(botToken);
        this.personService = personService;
        this.chartService = chartService;
    }

    //todo причесать код
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null) {
            Long userId = message.getFrom().getId();
            String text = message.getText();

            if (message.hasText()) {
                if (isCommand(message)) {
                    if (text.startsWith(HELP)) {
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

                        KeyboardRow r2 = new KeyboardRow();
                        KeyboardButton b3 = new KeyboardButton();
                        b3.setText(LAST_10);
                        KeyboardButton b4 = new KeyboardButton();
                        b4.setText(CHART);
                        r2.add(b3);
                        r2.add(b4);
                        keyboardRows.add(r2);

                        markup.setKeyboard(keyboardRows);

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());
                        sendMessage.setText("Список доступных команд:");
                        sendMessage.setReplyMarkup(markup);

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }
                    } else if (text.startsWith(INITIAL)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText(personService.get(userId).getInitialPretty());

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }
                    } else if (text.startsWith(CURRENT)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText(personService.get(userId).getCurrentPretty());

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }
                    } else if (text.startsWith(LAST_10)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText(personService.get(userId).getLastTenPretty());

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }
                    } else if (text.startsWith(CHART)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText(chartService.getChart(userId));

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }
                    } else if (text.startsWith(START)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText("Напиши свой вес (например 65.4) или посмотри команды отправив /help");

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            log.error(e.getMessage());
                        }

                        personService.register(userId, message.getChatId());
                    }
                } else {
                    double weight;

                    try {
                        weight = Double.parseDouble(text);

                        personService.addRecord(userId, weight);
                    } catch (NumberFormatException e) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());

                        sendMessage.setText("Напиши свой вес (например 65.4) или посмотри команды отправив /help");

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException te) {
                            log.error(te.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "WeightMinder";
    }

}
