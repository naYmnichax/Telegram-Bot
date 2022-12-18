package org.example.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SendBotMessageService {
    void sendMessage(String ChatId, String message);
    void sendMessageForCommandTranslate(SendMessage message, String chatId, String text);
    void sendMessageForButtonCommandTranslate(EditMessageText message, long chatId, long messageId, String text);
}
