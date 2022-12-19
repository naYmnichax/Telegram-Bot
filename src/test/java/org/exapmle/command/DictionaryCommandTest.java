package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.command.DictionaryCommand;
import org.example.command.TextCheckCommand;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class DictionaryCommandTest {
    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    @Test
    public void CheckInputIsEmptyDictionaryCommand() throws TelegramApiException{
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/random_text");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Введите число от 1 до 20");
        sendMessage.enableHtml(true);

        DictionaryCommand dictionaryCommand = new DictionaryCommand(sendBotMessageService);
        dictionaryCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }


    @Test
    public void CheckInputDictionaryCommand() throws TelegramApiException{
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/random_text ек");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Введите число от 1 до 20, а не другой символ.");
        sendMessage.enableHtml(true);

        DictionaryCommand dictionaryCommand = new DictionaryCommand(sendBotMessageService);
        dictionaryCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }

    @Test
    public void CheckInputCheckTextCommand() throws TelegramApiException{
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/check_text ут mouse house");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Введённый вами язык не поддерживается. Введите:команда язык слова\nКак в примере:/text_check en house mouse...\nПосмотреть поддерживаемые языки можно через /supplang");
        sendMessage.enableHtml(true);

        TextCheckCommand textCheckCommand = new TextCheckCommand(sendBotMessageService);
        textCheckCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }


}