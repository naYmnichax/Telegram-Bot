package org.example.service;

import org.example.TelegramBot.DrDarkness;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {
    private final DrDarkness darknessBot;

    @Autowired
    public SendBotMessageServiceImpl(DrDarkness darknessBot){
        this.darknessBot = darknessBot;
    }

    @Override
    public void sendMessage(String chatId, String message){
        if (isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try{
            darknessBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageForCommandTranslate(SendMessage message, String chatId, String text) {

        message.setChatId(chatId);
        message.enableHtml(true);
        message.setText(text);

        try{
            darknessBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageForButtonCommandTranslate(EditMessageText message, long chatId, long messageId, String text) {

        message.setChatId(chatId);
        message.setText(text);
        message.setMessageId((int) messageId);

        try{
            darknessBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
