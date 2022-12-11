package org.example.TelegramBot;

import org.example.command.CommandContainer;
import static org.example.command.CommandName.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.example.service.SendBotMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;



@Component
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

    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    @Autowired
    public DrDarkness() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.findCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.findCommand(NO.getCommandName()).execute(update);
            }
        }
    }
}