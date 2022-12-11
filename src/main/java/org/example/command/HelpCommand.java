package org.example.command;

import org.example.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.example.command.CommandName.*;



public class HelpCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    private static final String COMMAND_PREFIX = "/";

    public static final String HELP_MESSAGE = String.format("<b>Дотупные команды:</b>\n\n"
            + "%s - начать работу со мной\n"
            + "%s - получить помощь в работе со мной\n"
            + "%s - получить информацию обо мне\n"
            + "%s - повторю ваше собощение как хрюшка-повторюшка\n"
            + "%s - переведу написанное слово/фразу\n",
            START.getCommandName(),
            HELP.getCommandName(),
            ABOUT.getCommandName(),
            ECHO.getCommandName(),
            TRANSLATE.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update){
        Message message = update.getMessage();
        String[] words = message.getText().trim().split(" ");
        if(words.length > 1) {
            if (words[1].startsWith(COMMAND_PREFIX)) {
                final String HELP_COMMAND = new GetHelpForCommand().getHelp(words[1]);
                sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_COMMAND);
            }
        }
        else {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
        }
    }
}
