package com.spomprt.weightminder.helper;

import org.telegram.telegrambots.meta.api.objects.Message;

public final class MessageHelper {

    public static boolean isCommand(Message message) {
        return message.getText().startsWith("/");
    }

}
