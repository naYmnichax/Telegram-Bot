package org.example.command;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.*;

public class DictionaryCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    private Timer mTimer = new Timer();

    public static HashMap<Long, List<String>> wordsUsers = new HashMap<>();//здесь будут храниться слова пользователей чтобы сравнивать их перевод

    public DictionaryCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update){
        Message message = update.getMessage();
        String[] received_message = message.getText().split(" "); //принимаем сообщение
        var userId= update.getMessage().getChatId();
        int count = Integer.parseInt(received_message[1]);  // количество рандомных слов

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

        if(mTimer != null){
            mTimer.cancel();
            mTimer.purge();

        }
        mTimer = new Timer();
        mTimer.schedule(reminder(),120000);

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),"Отправте ваш ответ через команду /text_check в соответствии с полученными словами");
    }

    private TimerTask reminder() {
        return new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                File usersData = new File("TelegramUsers");
                File mailing_list = new File("Mailing RandomTextCommand");

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

                sendBotMessageService.sendMessage(chatId[0],message + " " + EmojiParser.parseToUnicode(":monocle_face:"));
                mTimer.cancel();
            }
        };
    }
}