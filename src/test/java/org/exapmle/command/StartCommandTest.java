package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.command.Command;
import org.example.command.EchoCommand;
import org.example.command.StartCommand;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static org.example.command.CommandName.START;
import static org.example.command.StartCommand.START_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest {
    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);

    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    @Test
    public void shouldProperlyExecuteStartCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;
        String FistName = "Матвей";
        String LastName = "Матус";

        Chat chat = new Chat();
        chat.setFirstName(FistName);
        chat.setLastName(LastName);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(START.getCommandName());
        Mockito.when(message.getChat()).thenReturn(chat);
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(START_MESSAGE,"Матвей Матус"));
        sendMessage.enableHtml(true);


        StartCommand startCommand = new StartCommand(sendBotMessageService);
        startCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }

    @Test
    public void notLastName() throws TelegramApiException {
        Long chatId = 1234567824356L;
        String FistName = "Матвей";

        Chat chat = new Chat();
        chat.setFirstName(FistName);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(START.getCommandName());
        Mockito.when(message.getChat()).thenReturn(chat);
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(START_MESSAGE,"Матвей"));
        sendMessage.enableHtml(true);


        StartCommand startCommand = new StartCommand(sendBotMessageService);
        startCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }
}
