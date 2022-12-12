package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.command.Command;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
abstract class AbstractCommandTest {
    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        getCommand().execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }
}
