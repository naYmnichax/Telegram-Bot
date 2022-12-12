package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.command.EchoCommand;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.command.EchoCommand.ECHO_MESSAGE;

public class EchoCommandTest {

    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);

    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    @Test
    public void shouldProperlyExecuteEchoCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/echo Привет, Амиго!");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(ECHO_MESSAGE, " Привет, Амиго!"));
        sendMessage.enableHtml(true);

        EchoCommand echoCommand = new EchoCommand(sendBotMessageService);
        echoCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }

    @Test
    public void isEmptyText() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/echo");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Вы ничего не написали.\n Напишите команду /help");
        sendMessage.enableHtml(true);

        EchoCommand echoCommand = new EchoCommand(sendBotMessageService);
        echoCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }
}
