package org.example.command;




import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.*;

public class DictionaryCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static HashMap<Long, List<String>> wordsUsers = new HashMap<>();//здесь будут храниться слова пользователей чтобы сравнивать их перевод

    public DictionaryCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update){
        String[] received_message = update.getMessage().toString().split(" "); //принимаем сообщение
        var userId= update.getMessage().getChatId();
        int count = Integer.parseInt(received_message[1]);  // количество рандомных слов
        //SendMessage message = new SendMessage();

        String file = "dictionary";// задаем имя файла
        List<String> rawWordsList = new ArrayList<>();     //  создаём массив для слов

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String read;
            while ((read = br.readLine()) != null) {
                rawWordsList.add(read); //добавляем все слова
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }catch (IOException e){
            System.out.println("I/O error.");
        }
        Collections.shuffle(rawWordsList); // перемешиваем
        var wordsList = rawWordsList.subList(0,count);  //берём 20 слов
        wordsUsers.put(userId, wordsList);//добовляем их в словарь чтобы потом проверить
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.join(", ",wordsList));
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),"Отправте ваш ответ через команду /text_check в соответствии с полученными словами");
    }
}