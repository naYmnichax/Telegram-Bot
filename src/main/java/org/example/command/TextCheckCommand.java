package org.example.command;

import lombok.SneakyThrows;
import org.example.service.SendBotMessageService;
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
        String[] received_message = update.getMessage().toString().split(" "); //принимаем сообщение команда язык слова
        var userId = update.getMessage().getChatId();
        var len = received_message.length - 2;//сколько всех слов без 2 ненужных(команда и язык)
        var language = received_message[1]; //язык перевода
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
}
