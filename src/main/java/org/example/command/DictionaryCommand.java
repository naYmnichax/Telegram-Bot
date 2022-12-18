package org.example.command;




import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public DictionaryCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update){
        String[] received_message = update.getMessage().toString().split(" "); //принимаем сообщение
        int count = Integer.parseInt(received_message[1]);  // количество рандомных слов
        SendMessage message = new SendMessage();

        String file = "dictionary";// задаем имя файла
        List<String> wordsList = new ArrayList<>();
        List<String> rawWordsList = new ArrayList<>();     //  создаём массив для слов



        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String read;
            while ((read = br.readLine()) != null) {
                rawWordsList.add(read);
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }catch (IOException e){
            System.out.println("I/O error.");
        }
        Collections.shuffle(rawWordsList); // перемешиваем

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.join(", ",rawWordsList.subList(0,count)));

    }



}