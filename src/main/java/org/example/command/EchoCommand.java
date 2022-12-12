package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

import static org.example.command.CommandName.ECHO;

public class EchoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String ECHO_MESSAGE = "Вы только что написали:\"%s\"";
    public final static String ECHO_HELP = String.format("%s - повторю ваше собощение как хрюшка-повторюшка", ECHO.getCommandName());

    public EchoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        StringBuilder answer = new StringBuilder();
        Message message = update.getMessage();
        String[] words = message.getText().trim().split(" ");
        if (words.length == 1) {
            final String EMPTY_MESSAGE = "Вы ничего не написали.\n Напишите команду /help";
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), EMPTY_MESSAGE);
        } else {
            for (int i = 1; i < words.length; ++i) {
                answer.append(" ").append(words[i]);
            }
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(ECHO_MESSAGE, answer));
        }
    }
}
