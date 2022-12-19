package org.exapmle.command;

import org.example.TelegramBot.DrDarkness;
import org.example.button.ButtonForTranslateCommand;
import org.example.command.TranslateCommand;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.command.CommandName.START;
import static org.example.command.TranslateCommand.TRANSLATE_MESSAGE;

@DisplayName("Unit-level testing for TranslateCommand")
public class TranslateCommandTest {
    protected DrDarkness drDarkness = Mockito.mock(DrDarkness.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);

    @Test
    public void  checkFirsStageCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("/translate");
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Выберите язык, с которого хотете перевести слово/фразу/предложение.");
        sendMessage.enableHtml(true);

        ButtonForTranslateCommand button = new ButtonForTranslateCommand();
        button.createButtonLanguageFrom(sendMessage);

        TranslateCommand translateCommand = new TranslateCommand(sendBotMessageService);
        translateCommand.execute(update);

        Mockito.verify(drDarkness).execute(sendMessage);
    }

    @Test
    public void  checkSecondStageCommand() throws TelegramApiException {
        Long chatId = 1234567824356L;
        long messageId = 123456243L;

        Chat chat = new Chat();
        chat.setId(chatId);



        Update update = new Update();
        Message message = new Message();
        message.setChat(chat);
        message.setMessageId((int) messageId);

        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Mockito.when(callbackQuery.getMessage()).thenReturn(message);
        Mockito.when(callbackQuery.getMessage().getChatId()).thenReturn(chatId);
        Mockito.when(callbackQuery.getMessage().getMessageId()).thenReturn((int) messageId);
        update.setEditedMessage(message);

        EditMessageText Message = new EditMessageText();
        Message.setChatId(chatId.toString());
        Message.setMessageId((int) messageId);
        Message.setText("Выберите язык, на который хотите перевести слово/фразу/предложения.");

        ButtonForTranslateCommand button = new ButtonForTranslateCommand();
        button.createButtonLanguageTo(Message);

        TranslateCommand translateCommand = new TranslateCommand(sendBotMessageService);
        translateCommand.execute(update);

        Mockito.verify(drDarkness).execute(Message);
    }
}
