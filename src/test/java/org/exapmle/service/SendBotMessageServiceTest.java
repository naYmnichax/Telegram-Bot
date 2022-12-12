package org.exapmle.service;

import org.example.TelegramBot.DrDarkness;
import org.example.service.SendBotMessageService;
import org.example.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private DrDarkness drDarkness;

    @BeforeEach
    public void init() {
        drDarkness = Mockito.mock(DrDarkness.class);
        sendBotMessageService = new SendBotMessageServiceImpl(drDarkness);
    }

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        Long chatId = 123L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);

        sendBotMessageService.sendMessage(String.valueOf(chatId), message);

        Mockito.verify(drDarkness).execute(sendMessage);
    }
}
