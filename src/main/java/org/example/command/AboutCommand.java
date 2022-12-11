package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.command.CommandName.ABOUT;

public class AboutCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String ABOUT_MESSAGE = "Я бот, который поможет вам с переводом слов/фраз.\n"
            + "Меня написал @Dr_DarkNessss";
    public static final String ABOUT_HELP = String.format("%s - получить информацию обо мне", ABOUT.getCommandName());
    public AboutCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ABOUT_MESSAGE);
    }
}
