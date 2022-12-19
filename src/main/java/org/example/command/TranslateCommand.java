package org.example.command;

import lombok.SneakyThrows;
import org.example.button.ButtonForTranslateCommand;
import org.example.service.SendBotMessageService;
import org.example.translateAPI.Translator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;


import static org.example.command.CommandName.*;

public class TranslateCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    private static short history = 0;
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
}
