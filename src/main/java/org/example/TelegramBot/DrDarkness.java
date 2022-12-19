package org.example.TelegramBot;

import lombok.SneakyThrows;
import org.example.command.CommandContainer;

import static org.example.command.CommandName.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.example.service.SendBotMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Scanner;

@Component
public class DrDarkness extends TelegramLongPollingBot {
    private static final String BOT_NAME = new InfoBot().getInfo("Bot_Name.txt");
    private static final String BOT_TOKEN = new InfoBot().getInfo("Bot_Token.txt");

    public String LAST_COMMAND;

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
        commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                LAST_COMMAND = commandIdentifier;
                if(commandIdentifier.equals(START.getCommandName())){
                    registerUser(update.getMessage());
                }
                commandContainer.findCommand(commandIdentifier).execute(update);
            } else {
                if (LAST_COMMAND.equals(TRANSLATE.getCommandName())) {
                    commandContainer.findCommand(TRANSLATE.getCommandName()).execute(update);
                    LAST_COMMAND = "";
                } else {
                    commandContainer.findCommand(NO.getCommandName()).execute(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            commandContainer.findCommand(TRANSLATE.getCommandName()).execute(update);
        }
    }

    @SneakyThrows
    private void registerUser(Message message) {

        String chatId = String.valueOf(message.getChatId());

        if(!checkExistsUser(chatId)) {
            FileOutputStream users = new FileOutputStream("TelegramUsers", true);

            String FirstName = message.getChat().getFirstName();
            String LastName = message.getChat().getLastName();
            String UserName = message.getChat().getUserName();
            Timestamp timeRegister = new Timestamp(System.currentTimeMillis());

            String userData = chatId + " " + UserName + " " + FirstName + " " + LastName + " " + timeRegister + "\n";

            users.write(userData.getBytes());

            users.close();
        }
    }

    @SneakyThrows
    private boolean checkExistsUser(String chatId) {

        File file = new File("TelegramUsers");
        Scanner users = new Scanner(file);
        String user = "firstUser";

        if(users.hasNextLine()){
            user = users.nextLine();
        }

        String[] userData = user.split(" ");
        return chatId.equals(userData[0]);
    }
}