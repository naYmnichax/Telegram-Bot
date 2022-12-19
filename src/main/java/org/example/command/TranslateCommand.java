package org.example.command;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.example.button.ButtonForTranslateCommand;
import org.example.service.SendBotMessageService;
import org.example.translateAPI.Translator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static org.example.command.CommandName.*;

public class TranslateCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private Timer mTimer = new Timer();

    public static short history = 0;
    private static String LANGUAGE_FROM;
    private static String LANGUAGE_TO;


    public final static String TRANSLATE_MESSAGE = "Ваше переведённая слово/фраза/предложение: \"%s\"";

    public final static String TRANSLATE_HELP = String.format("%s - данная команда поможет вам перевести нужное вам слово/фразу/преложение.", TRANSLATE.getCommandName());

    public TranslateCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @SneakyThrows
    @Override
    public void execute(Update update) {

        if (history == 0){

            history++;

            SendMessage message = new SendMessage();
            String text = "Выберите язык, с которого хотете перевести слово/фразу/предложение.";

            ButtonForTranslateCommand button = new ButtonForTranslateCommand();
            button.createButtonLanguageFrom(message);

            sendBotMessageService.sendMessageForCommandTranslate(message, update.getMessage().getChatId().toString(), text);

        } else if (history == 1) {
            history++;

            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            EditMessageText message = new EditMessageText();
            String text = "Выберите язык, на который хотите перевести слово/фразу/предложения.";

            LANGUAGE_FROM = languageFrom(callbackData);

            ButtonForTranslateCommand button = new ButtonForTranslateCommand();
            button.createButtonLanguageTo(message);

            sendBotMessageService.sendMessageForButtonCommandTranslate(message, chatId, messageId, text);

        } else if (history == 2) {
            history++;

            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            EditMessageText message = new EditMessageText();
            String text = "Напишите слово/фразу/предложение которое хотите перевести.";

            LANGUAGE_TO = languageTo(callbackData);

            sendBotMessageService.sendMessageForButtonCommandTranslate(message, chatId, messageId, text);

        } else {
            history = 0;

            if(mTimer != null){
                mTimer.cancel();
                mTimer.purge();

            }
            mTimer = new Timer();
            mTimer.schedule(reminder(),120000);

            Translator translator = new Translator();
            String textTranslate = String.format(TRANSLATE_MESSAGE, translator.translate(LANGUAGE_FROM, LANGUAGE_TO, update.getMessage().getText().trim()));

            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), textTranslate);
        }

    }

    private static String languageFrom(String callBackData) {
        return switch (callBackData) {
            case "LanguageFrom_EN" -> "en";
            case "LanguageFrom_DE" -> "de";
            case "LanguageFrom_RU" -> "ru";
            case "LanguageFrom_KZ" -> "kk";
            default -> null;
        };
    }

    private static String languageTo(String callBackData) {
        return switch (callBackData) {
            case "LanguageTo_EN" -> "en";
            case "LanguageTo_DE" -> "de";
            case "LanguageTo_RU" -> "ru";
            case "LanguageTo_KZ" -> "kk";
            default -> null;
        };
    }

    private TimerTask reminder() {
        return new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                File usersData = new File("TelegramUsers");
                File mailing_list = new File("Mailing TranslateCommand");

                Scanner user = new Scanner(usersData);
                Scanner mailing = new Scanner(mailing_list);

                String data = "";
                if(user.hasNextLine()){
                    data = user.nextLine();
                }
                String[] chatId = data.split(" ");

                String message = "";
                while (mailing.hasNextLine()) {
                    message += mailing.nextLine();
                    if(mailing.hasNextLine()){
                        message += "\n";
                    }
                }

                sendBotMessageService.sendMessage(chatId[0],message + " " + EmojiParser.parseToUnicode(":face_with_monocle:"));
                mTimer.cancel();
            }
        };
    }
}
