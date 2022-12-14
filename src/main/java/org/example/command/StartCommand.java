package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.example.command.CommandName.START;


public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Здравствуйте, %s.\n" +
            "Я бот переводчик. И я вам постраюсь помочь в вашей нелёгкой жизне по изучению Английского языка";
    public final static String START_HELP = String.format("%s - начать работу со мной", START.getCommandName());

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        final String FirstName = update.getMessage().getChat().getFirstName();
        final String LastName = update.getMessage().getChat().getLastName();
        if (LastName != null) {
            final String NameUser = FirstName + " " + LastName;
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(START_MESSAGE, NameUser));
        } else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(START_MESSAGE, FirstName));
        }
    }
}
