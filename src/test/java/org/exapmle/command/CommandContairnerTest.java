package org.exapmle.command;



import org.example.service.SendBotMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.example.command.*;

import static org.example.command.CommandName.*;


@DisplayName("Unit-level testing for CommandContainer")
public class CommandContairnerTest {

    private CommandContainer commandContairner;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        commandContairner = new CommandContainer(sendBotMessageService);
    }

    @Test
    public void shouldReturnStartCommand() {
        String startCommand = START.getCommandName();

        Command command = commandContairner.findCommand(startCommand);

        Assertions.assertEquals(StartCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnHelpCommand() {
        String startCommand = HELP.getCommandName();

        Command command = commandContairner.findCommand(startCommand);

        Assertions.assertEquals(HelpCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnAboutCommand() {
        String startCommand = ABOUT.getCommandName();

        Command command = commandContairner.findCommand(startCommand);

        Assertions.assertEquals(AboutCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnEchoCommand() {
        String startCommand = ECHO.getCommandName();

        Command command = commandContairner.findCommand(startCommand);

        Assertions.assertEquals(EchoCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnNoCommand() {
        String startCommand = NO.getCommandName();

        Command command = commandContairner.findCommand(startCommand);

        Assertions.assertEquals(NoCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/fgjhdfgdfg";

        Command command = commandContairner.findCommand(unknownCommand);

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
