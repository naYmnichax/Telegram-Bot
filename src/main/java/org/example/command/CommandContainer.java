package org.example.command;

import org.example.service.SendBotMessageService;
import  com.google.common.collect.ImmutableMap;

import static org.example.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(ABOUT.getCommandName(), new AboutCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(ECHO.getCommandName(), new EchoCommand(sendBotMessageService))
                .put(TRANSLATE.getCommandName(), new TranslateCommand(sendBotMessageService))
                .put(SUPPORTED_LANGUAGES.getCommandName(), new SupLangCommand(sendBotMessageService))
                .put(RND_TEXT.getCommandName(), new DictionaryCommand(sendBotMessageService))
                .put(TEXT_CHECK.getCommandName(), new TextCheckCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
