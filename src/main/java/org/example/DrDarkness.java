package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;


public class DrDarkness extends TelegramLongPollingBot {
    private static final String BOT_NAME = new InfoBot().getInfo("Bot_Name.txt");
    private static final String BOT_TOKEN = new InfoBot().getInfo("Bot_Token.txt");

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            String  chatId = update.getMessage().getChatId().toString();
            Message message = update.getMessage();
            String  username = message.getChat().getFirstName() + " " + message.getChat().getLastName();
            if (message.hasText()){
                Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
                if (commandEntity.isPresent()) {
                    String instruction = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                    String text = message.getText().substring(commandEntity.get().getLength());
                    String answer = new Command().SetCommand(instruction, text, username);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(answer);
                    sendMessage.setChatId(chatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}