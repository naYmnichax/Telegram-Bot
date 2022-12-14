package org.example.command;

import lombok.SneakyThrows;
import org.example.translateAPI.SupportedLanguages;
import org.example.translateAPI.Translator;
import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.command.CommandName.*;


public class TranslateCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public final static String TRANSLATE_MESSAGE = "Ваше переведённая фараза/предложение: \"%s\"";

    public final static String TRANSLATE_HELP = String.format("Формат ввода: \"%s [язык на котором написаны слова] [язык на который нужно перевести слова] [слова/фразы/предложения которые надо перевести]\"", TRANSLATE.getCommandName());

    public TranslateCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @SneakyThrows
    @Override
    public void execute(Update update) {
        StringBuilder sentence = new StringBuilder();
        Message message = update.getMessage();
        String[] text = message.getText().trim().split(" ");
        if(text.length <= 3) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Вы ввели пустое собщение. Напишите пожалуйста:\"%s %s\"",
                    HELP.getCommandName(),
                    TRANSLATE.getCommandName()));
        } else {
            String langFrom = text[1];
            String langTo = text[2];

            SupportedLanguages supportedLanguages = new SupportedLanguages();
            if(!supportedLanguages.checkLanguages(langFrom, langTo) ) {
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format("Введённый вами язык не поддерживается.\nНапишите пожалуйста:\n\"%s %s\" или \"%s\"",
                        HELP.getCommandName(),
                        TRANSLATE.getCommandName(),
                        SUPPORTED_LANGUAGES.getCommandName()));
            } else {
                for(int i = 3; i < text.length; ++i) {
                    sentence.append(text[i]);
                    if(i != text.length-1) {
                        sentence.append(" ");
                    }
                }
                Translator translator = new Translator();

                String translateSentence = translator.translate(langFrom, langTo, String.valueOf(sentence));
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(TRANSLATE_MESSAGE,translateSentence));
            }
        }
    }
}
