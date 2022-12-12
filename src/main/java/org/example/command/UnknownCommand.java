package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command{

    public static final String UNKNOWN_MESSAGE = "Не понимаю тебя, напиши пожалуйста /help";

    private final SendBotMessageService sendBotMessageService;

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
