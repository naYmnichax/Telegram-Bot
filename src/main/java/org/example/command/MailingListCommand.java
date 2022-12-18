package org.example.command;

import lombok.SneakyThrows;
import org.example.service.SendBotMessageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Scanner;

public class MailingListCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public MailingListCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @SneakyThrows
    @Override
    @Scheduled(cron = "0 * * * * *")
    public void execute(Update update) {

        File usersData = new File("TelegramUsers");
        File mailing_list = new File("Mailing list");

        Scanner user = new Scanner(usersData);
        Scanner mailing = new Scanner(mailing_list);

        String data = "";
        if(user.hasNextLine()){
            data = user.nextLine();
        }
        String[] chatId = data.split(" ");

        String message = "";
        if(mailing.hasNextLine()) {
            message = mailing.nextLine();
        }

        sendBotMessageService.sendMessage(chatId[0], message);
    }
}
