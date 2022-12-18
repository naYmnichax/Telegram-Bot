package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.command.CommandName.HELP;

public class UnknownCommand implements Command{

    public static final String UNKNOWN_MESSAGE = String.format("Не понимаю тебя, напиши пожалуйста %s", HELP.getCommandName());

    private final SendBotMessageService sendBotMessageService;

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
