package org.example.command;

import lombok.SneakyThrows;
import org.example.service.SendBotMessageService;
import org.example.translateAPI.SupportedLanguages;
import org.example.translateAPI.Translator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;

public class TextCheckCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public TextCheckCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @SneakyThrows
    @Override
    public void execute(Update update){
        String[] received_message = update.getMessage().getText().split(" "); //принимаем сообщение команда язык слова
        var userId = update.getMessage().getChatId();
        int len;
        String language;

        if (received_message.length > 1){
            language = received_message[1]; //язык перевода
            len = received_message.length - 2;//сколько всех слов без 2 ненужных(команда и язык)
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Некорректный ввод. введите /text_check язык слова\nКак в примере:/text_check en house mouse bed cat");
            return;
        }


        if (SupportedLanguages.checkLanguages(language)) {      //Проверка на поддерживаемый язык
            List<String> words = DictionaryCommand.wordsUsers.get(userId);
            HashMap<Integer, String> matches = new HashMap<>();

            Translator translator = new Translator();

            for (int i = 0; i < len; i++) {

                if(translator.translate("ru", language, words.get(i)).equals(received_message[i + 2])){
                    matches.put(i + 1,"Правильно");       //сравниваем переводы слов бота с пользователем
                }

                else {
                    matches.put(i + 1, "Неправильно");
                }
            }
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), matches.toString());
            DictionaryCommand.wordsUsers.remove(userId);
        }

        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Введённый вами язык не поддерживается. Введите:команда язык слова\nКак в примере:/text_check en house mouse...\nПосмотреть поддерживаемые языки можно через /supplang");
        }

    }
}
