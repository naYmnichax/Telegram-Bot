package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.command.TranslateCommand;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.command.TranslateCommand.TRANSLATE_MESSAGE;

@DisplayName("Unit-level testing for TranslateCommand")
public class TranslateCommandTest {
    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    @Test
    public void  shouldProperlyExecuteTranslateCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/translate ru en Привет, мир!");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(TRANSLATE_MESSAGE, "Hello World!"));
        sendMessage.enableHtml(true);

        TranslateCommand translateCommand = new TranslateCommand(sendBotMessageService);
        translateCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }

    @Test
    public void  checkSupportedLanguages() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/translate ru pp Привет, мир!");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Введённый вами язык не поддерживается.");
        sendMessage.enableHtml(true);

        TranslateCommand translateCommand = new TranslateCommand(sendBotMessageService);
        translateCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }
}
