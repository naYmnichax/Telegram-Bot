package org.example;

import org.example.TelegramBot.DrDarkness;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@EnableScheduling
public class BotApplication {
    public static void main(String[] args){
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new DrDarkness());
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
