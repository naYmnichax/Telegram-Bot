package org.example.command;

import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.*;

import static org.example.command.CommandName.TEXT_CHECK;

public class DictionaryCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    private Timer mTimer = new Timer();


    public static HashMap<Long, List<String>> wordsUsers = new HashMap<>();//здесь будут храниться слова пользователей чтобы сравнивать их перевод
    public final static String TEXT_HELP = String.format("%s данная команда принимает данные в виде язык слова через пробел\nдля примера:/text_check en bed cat mouse house",TEXT_CHECK.getCommandName());

    public DictionaryCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update){
        Message message = update.getMessage();
        String[] received_message = message.getText().split(" "); //принимаем сообщение
        var userId= message.getChatId();
        int count;
        String number;

        if (received_message.length>1) {
            number = received_message[1];  //Проверка на пустую /text_check
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Введите число от 1 до 20");
            return;
        }

        if(Character.isDigit(number.charAt(0)) && Character.isDigit(number.charAt(1)) && number.length()<3){
            count = Integer.parseInt(number);  // количество рандомных слов
        }                    //проверка на то что у нас от 1 до 20 и это цифра
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), "Введите число от 1 до 20, а не другой символ.");
            return;
        }

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

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),"Отправте ваш ответ в виде: команда язык слова через пробел; через команду /text_check в соответствии с полученными словами");
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